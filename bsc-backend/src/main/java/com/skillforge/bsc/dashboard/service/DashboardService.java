package com.skillforge.bsc.dashboard.service;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b4.repository.FinalStrategicObjectiveRepository;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b5.repository.DepartmentKpiRepository;
import com.skillforge.bsc.bsc.b5.repository.DepartmentParticipationRepository;
import com.skillforge.bsc.bsc.b6.entity.KpiWeight;
import com.skillforge.bsc.bsc.b6.entity.ObjectiveWeight;
import com.skillforge.bsc.bsc.b6.entity.PerspectiveWeight;
import com.skillforge.bsc.bsc.b6.repository.KpiWeightRepository;
import com.skillforge.bsc.bsc.b6.repository.ObjectiveWeightRepository;
import com.skillforge.bsc.bsc.b6.repository.PerspectiveWeightRepository;
import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b7.repository.KpiMeasurementRepository;
import com.skillforge.bsc.bsc.b8.entity.ActionPlan;
import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.repository.ActionPlanRepository;
import com.skillforge.bsc.bsc.b8.repository.KpiReportRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.repository.BscStrategyRepository;
import com.skillforge.bsc.common.enums.*;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.dashboard.dto.response.*;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BscStrategyRepository bscStrategyRepository;
    private final DepartmentRepository departmentRepository;
    private final FinalStrategicObjectiveRepository finalObjectiveRepository;
    private final DepartmentParticipationRepository participationRepository;
    private final DepartmentKpiRepository departmentKpiRepository;
    private final PerspectiveWeightRepository perspectiveWeightRepository;
    private final ObjectiveWeightRepository objectiveWeightRepository;
    private final KpiWeightRepository kpiWeightRepository;
    private final KpiMeasurementRepository measurementRepository;
    private final KpiReportRepository reportRepository;
    private final ActionPlanRepository actionPlanRepository;
    private final TaskRepository taskRepository;
    private final DashboardCalculationService calculationService;

    @Transactional(readOnly = true)
    public CompanyDashboardResponse getCompanyDashboard(UUID strategyId) {
        Snapshot snapshot = loadSnapshot(getStrategy(strategyId));
        BigDecimal companyScore = sumScores(snapshot.kpis());
        LocalDate today = LocalDate.now();

        List<CompanyDashboardResponse.PerspectiveSummary> perspectives = Arrays.stream(BscPerspectiveCode.values())
                .map(code -> perspectiveSummary(snapshot, code))
                .toList();
        List<CompanyDashboardResponse.ObjectiveSummary> objectives = snapshot.objectives().stream()
                .map(objective -> companyObjectiveSummary(snapshot, objective, today))
                .toList();
        List<CompanyDashboardResponse.DepartmentSummary> departments = snapshot.departments().stream()
                .map(department -> companyDepartmentSummary(snapshot, department, today))
                .toList();

        return CompanyDashboardResponse.builder()
                .strategy(strategySummary(snapshot.strategy()))
                .companyScore(companyScore)
                .companyCompletionRate(companyScore)
                .statusColor(calculationService.aggregateStatusColor(companyScore))
                .perspectives(perspectives)
                .objectives(objectives)
                .departments(departments)
                .kpiSummary(summarizeKpis(snapshot.kpis()))
                .taskSummary(calculationService.summarizeTasks(snapshot.tasks(), today))
                .reportMissingCount(snapshot.kpis().stream()
                        .filter(kpi -> kpi.score().getDataStatus() == DashboardScoringDataStatus.MISSING_REPORT)
                        .count())
                .asOfDate(today)
                .build();
    }

    @Transactional(readOnly = true)
    public ObjectiveDashboardResponse getObjectiveDashboard(UUID strategyId) {
        Snapshot snapshot = loadSnapshot(getStrategy(strategyId));
        LocalDate today = LocalDate.now();
        List<ObjectiveDashboardResponse.ObjectiveDetail> objectives = snapshot.objectives().stream()
                .map(objective -> objectiveDetail(snapshot, objective, today))
                .toList();
        return ObjectiveDashboardResponse.builder()
                .strategy(strategySummary(snapshot.strategy()))
                .objectives(objectives)
                .asOfDate(today)
                .build();
    }

    @Transactional(readOnly = true)
    public DepartmentDashboardResponse getDepartmentDashboard(UUID strategyId, UUID departmentId) {
        BscStrategy strategy = getStrategy(strategyId);
        Department department = departmentRepository.findByIdAndCompanyId(departmentId, strategy.getCompany().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.DEPARTMENT_NOT_FOUND));
        Snapshot snapshot = loadSnapshot(strategy);
        LocalDate today = LocalDate.now();
        List<KpiView> kpis = kpisForDepartment(snapshot, departmentId);
        List<Task> tasks = tasksForKpis(snapshot, kpis);
        List<ActionPlan> actionPlans = plansForKpis(snapshot, kpis);
        BigDecimal allocatedWeight = sumWeights(kpis);
        BigDecimal score = sumScores(kpis);
        BigDecimal completion = calculationService.aggregateCompletion(score, allocatedWeight);

        return DepartmentDashboardResponse.builder()
                .strategy(strategySummary(strategy))
                .department(departmentSummary(department))
                .allocatedKpiWeightPercent(allocatedWeight)
                .weightedScore(score)
                .completionRate(completion)
                .statusColor(calculationService.aggregateStatusColor(completion))
                .kpis(kpis.stream().map(kpi -> kpiItem(kpi, today)).toList())
                .actionPlans(actionPlans.stream()
                        .map(plan -> actionPlanResponse(plan, tasksForPlan(snapshot, plan.getId()), today, false))
                        .toList())
                .taskSummary(calculationService.summarizeTasks(tasks, today))
                .overdueCount(tasks.stream().filter(task -> calculationService.isOverdue(task, today)).count())
                .blockedCount(tasks.stream().filter(task -> task.getStatus() == TaskStatus.BLOCKED).count())
                .asOfDate(today)
                .build();
    }

    @Transactional(readOnly = true)
    public KpiDashboardDetailResponse getKpiDashboard(UUID departmentKpiId) {
        DepartmentKpi kpi = departmentKpiRepository.findById(departmentKpiId)
                .filter(item -> item.getStatus() == DepartmentKpiStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_KPI_NOT_FOUND));
        Snapshot snapshot = loadSnapshot(kpi.getBscStrategy());
        KpiView view = snapshot.kpis().stream()
                .filter(item -> item.kpi().getId().equals(departmentKpiId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.B5_KPI_NOT_FOUND));
        LocalDate today = LocalDate.now();
        KpiReport selectedReport = view.selection().report();

        return KpiDashboardDetailResponse.builder()
                .strategy(strategySummary(snapshot.strategy()))
                .kpi(KpiDashboardDetailResponse.KpiInfo.builder()
                        .departmentKpiId(kpi.getId())
                        .name(kpi.getName())
                        .description(kpi.getDescription())
                        .displayOrder(kpi.getDisplayOrder())
                        .build())
                .finalObjective(KpiDashboardDetailResponse.ObjectiveInfo.builder()
                        .finalObjectiveId(kpi.getFinalStrategicObjective().getId())
                        .name(kpi.getFinalStrategicObjective().getName())
                        .description(kpi.getFinalStrategicObjective().getDescription())
                        .perspectiveCode(kpi.getFinalStrategicObjective().getPerspectiveCode())
                        .build())
                .department(departmentSummary(kpi.getDepartment()))
                .weightPercent(view.weight())
                .measurement(measurementSummary(view.measurement()))
                .selectedReport(reportSummary(selectedReport, true))
                .scoringDataStatus(view.score().getDataStatus())
                .missingReport(view.score().getDataStatus() == DashboardScoringDataStatus.MISSING_REPORT)
                .provisional(view.score().getDataStatus() == DashboardScoringDataStatus.PROVISIONAL)
                .completionRate(view.score().getCompletionRate())
                .scoreCompletion(view.score().getScoreCompletion())
                .weightedScore(view.score().getWeightedScore())
                .statusColor(view.score().getStatusColor())
                .achievementStatus(view.score().getAchievementStatus())
                .taskSummary(calculationService.summarizeTasks(view.tasks(), today))
                .actionPlans(view.actionPlans().stream()
                        .map(plan -> actionPlanResponse(plan, tasksForPlan(snapshot, plan.getId()), today, true))
                        .toList())
                .reportHistory(view.reports().stream()
                        .sorted(Comparator.comparing(KpiReport::getCreatedAt).reversed())
                        .map(report -> reportSummary(report,
                                selectedReport != null && selectedReport.getId().equals(report.getId())))
                        .toList())
                .asOfDate(today)
                .build();
    }

    private Snapshot loadSnapshot(BscStrategy strategy) {
        UUID strategyId = strategy.getId();
        List<FinalStrategicObjective> objectives = finalObjectiveRepository
                .findByBscStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                        strategyId, StrategicObjectiveStatus.ACTIVE);
        List<DepartmentKpi> kpis = departmentKpiRepository
                .findByBscStrategy_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(strategyId, DepartmentKpiStatus.ACTIVE);

        Map<UUID, BigDecimal> kpiWeights = kpiWeightRepository.findByBscStrategy_Id(strategyId).stream()
                .collect(Collectors.toMap(weight -> weight.getDepartmentKpi().getId(), KpiWeight::getWeightPercent));
        Map<UUID, BigDecimal> objectiveWeights = objectiveWeightRepository.findByBscStrategy_Id(strategyId).stream()
                .collect(Collectors.toMap(weight -> weight.getFinalStrategicObjective().getId(), ObjectiveWeight::getWeightPercent));
        Map<BscPerspectiveCode, BigDecimal> perspectiveWeights = perspectiveWeightRepository.findByBscStrategy_Id(strategyId)
                .stream().collect(Collectors.toMap(PerspectiveWeight::getPerspectiveCode, PerspectiveWeight::getWeightPercent));
        Map<UUID, KpiMeasurement> measurements = measurementRepository
                .findByBscStrategy_IdAndStatus(strategyId, MeasurementStatus.ACTIVE).stream()
                .collect(Collectors.toMap(measurement -> measurement.getDepartmentKpi().getId(), Function.identity()));

        List<KpiReport> reports = reportRepository.findByBscStrategy_IdOrderByReportingPeriodDescCreatedAtDesc(strategyId);
        List<ActionPlan> actionPlans = actionPlanRepository.findByBscStrategy_IdOrderByStartDateAscCreatedAtAsc(strategyId);
        List<Task> tasks = taskRepository.findByBscStrategy_IdOrderByDueDateAscCreatedAtAsc(strategyId);
        Map<UUID, List<KpiReport>> reportsByKpi = reports.stream()
                .collect(Collectors.groupingBy(report -> report.getDepartmentKpi().getId()));
        Map<UUID, List<ActionPlan>> plansByKpi = actionPlans.stream()
                .collect(Collectors.groupingBy(plan -> plan.getDepartmentKpi().getId()));
        Map<UUID, List<Task>> tasksByKpi = tasks.stream()
                .collect(Collectors.groupingBy(task -> task.getDepartmentKpi().getId()));

        List<KpiView> views = kpis.stream().map(kpi -> {
            List<KpiReport> kpiReports = reportsByKpi.getOrDefault(kpi.getId(), List.of());
            DashboardCalculationService.ScoringReportSelection selection = calculationService.selectScoringReport(kpiReports);
            BigDecimal weight = calculationService.scale(kpiWeights.get(kpi.getId()));
            KpiMeasurement measurement = measurements.get(kpi.getId());
            return new KpiView(
                    kpi,
                    weight,
                    measurement,
                    kpiReports,
                    selection,
                    calculationService.calculateKpiScore(measurement, selection, weight),
                    plansByKpi.getOrDefault(kpi.getId(), List.of()),
                    tasksByKpi.getOrDefault(kpi.getId(), List.of())
            );
        }).toList();

        Map<UUID, Department> departments = new LinkedHashMap<>();
        participationRepository.findByBscStrategy_IdAndStatusOrderByCreatedAtAsc(
                        strategyId, DepartmentParticipationStatus.ACTIVE)
                .forEach(participation -> departments.put(participation.getDepartment().getId(), participation.getDepartment()));
        kpis.forEach(kpi -> departments.put(kpi.getDepartment().getId(), kpi.getDepartment()));

        return new Snapshot(
                strategy,
                objectives,
                new ArrayList<>(departments.values()),
                views,
                perspectiveWeights,
                objectiveWeights,
                actionPlans,
                tasks
        );
    }

    private CompanyDashboardResponse.PerspectiveSummary perspectiveSummary(Snapshot snapshot, BscPerspectiveCode code) {
        List<KpiView> kpis = snapshot.kpis().stream()
                .filter(kpi -> kpi.kpi().getFinalStrategicObjective().getPerspectiveCode() == code)
                .toList();
        BigDecimal weight = calculationService.scale(snapshot.perspectiveWeights().get(code));
        BigDecimal score = sumScores(kpis);
        BigDecimal completion = calculationService.aggregateCompletion(score, weight);
        return CompanyDashboardResponse.PerspectiveSummary.builder()
                .perspectiveCode(code)
                .weightPercent(weight)
                .weightedScore(score)
                .completionRate(completion)
                .statusColor(calculationService.aggregateStatusColor(completion))
                .objectiveCount((int) snapshot.objectives().stream()
                        .filter(objective -> objective.getPerspectiveCode() == code).count())
                .kpiCount(kpis.size())
                .missingReportCount(kpis.stream()
                        .filter(kpi -> kpi.score().getDataStatus() == DashboardScoringDataStatus.MISSING_REPORT).count())
                .build();
    }

    private CompanyDashboardResponse.ObjectiveSummary companyObjectiveSummary(
            Snapshot snapshot, FinalStrategicObjective objective, LocalDate today) {
        List<KpiView> kpis = kpisForObjective(snapshot, objective.getId());
        List<Task> tasks = tasksForKpis(snapshot, kpis);
        BigDecimal weight = calculationService.scale(snapshot.objectiveWeights().get(objective.getId()));
        BigDecimal score = sumScores(kpis);
        BigDecimal completion = calculationService.aggregateCompletion(score, weight);
        return CompanyDashboardResponse.ObjectiveSummary.builder()
                .objectiveId(objective.getId())
                .objectiveName(objective.getName())
                .perspectiveCode(objective.getPerspectiveCode())
                .objectiveWeightPercent(weight)
                .weightedScore(score)
                .completionRate(completion)
                .statusColor(calculationService.aggregateStatusColor(completion))
                .departmentCount((int) kpis.stream().map(kpi -> kpi.kpi().getDepartment().getId()).distinct().count())
                .kpiSummary(summarizeKpis(kpis))
                .taskSummary(calculationService.summarizeTasks(tasks, today))
                .build();
    }

    private CompanyDashboardResponse.DepartmentSummary companyDepartmentSummary(
            Snapshot snapshot, Department department, LocalDate today) {
        List<KpiView> kpis = kpisForDepartment(snapshot, department.getId());
        List<Task> tasks = tasksForKpis(snapshot, kpis);
        BigDecimal allocatedWeight = sumWeights(kpis);
        BigDecimal score = sumScores(kpis);
        BigDecimal completion = calculationService.aggregateCompletion(score, allocatedWeight);
        return CompanyDashboardResponse.DepartmentSummary.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .departmentCode(department.getCode())
                .departmentColor(department.getColor())
                .allocatedKpiWeightPercent(allocatedWeight)
                .weightedScore(score)
                .completionRate(completion)
                .statusColor(calculationService.aggregateStatusColor(completion))
                .kpiSummary(summarizeKpis(kpis))
                .taskSummary(calculationService.summarizeTasks(tasks, today))
                .build();
    }

    private ObjectiveDashboardResponse.ObjectiveDetail objectiveDetail(
            Snapshot snapshot, FinalStrategicObjective objective, LocalDate today) {
        List<KpiView> kpis = kpisForObjective(snapshot, objective.getId());
        List<Task> tasks = tasksForKpis(snapshot, kpis);
        BigDecimal objectiveWeight = calculationService.scale(snapshot.objectiveWeights().get(objective.getId()));
        BigDecimal score = sumScores(kpis);
        BigDecimal completion = calculationService.aggregateCompletion(score, objectiveWeight);
        return ObjectiveDashboardResponse.ObjectiveDetail.builder()
                .objectiveId(objective.getId())
                .objectiveName(objective.getName())
                .description(objective.getDescription())
                .displayOrder(objective.getDisplayOrder())
                .perspectiveCode(objective.getPerspectiveCode())
                .perspectiveWeightPercent(calculationService.scale(
                        snapshot.perspectiveWeights().get(objective.getPerspectiveCode())))
                .objectiveWeightPercent(objectiveWeight)
                .weightedScore(score)
                .completionRate(completion)
                .statusColor(calculationService.aggregateStatusColor(completion))
                .kpis(kpis.stream().map(kpi -> kpiItem(kpi, today)).toList())
                .actionPlanCount(kpis.stream().mapToInt(kpi -> kpi.actionPlans().size()).sum())
                .taskSummary(calculationService.summarizeTasks(tasks, today))
                .build();
    }

    private DashboardKpiItemResponse kpiItem(KpiView view, LocalDate today) {
        KpiReport report = view.selection().report();
        return DashboardKpiItemResponse.builder()
                .departmentKpiId(view.kpi().getId())
                .kpiName(view.kpi().getName())
                .finalObjectiveId(view.kpi().getFinalStrategicObjective().getId())
                .finalObjectiveName(view.kpi().getFinalStrategicObjective().getName())
                .departmentId(view.kpi().getDepartment().getId())
                .departmentName(view.kpi().getDepartment().getName())
                .departmentCode(view.kpi().getDepartment().getCode())
                .weightPercent(view.weight())
                .targetValue(view.measurement() == null ? null : view.measurement().getTargetValue())
                .actualValue(report == null ? null : report.getActualValue())
                .selectedReportingPeriod(report == null ? null : report.getReportingPeriod())
                .scoringDataStatus(view.score().getDataStatus())
                .completionRate(view.score().getCompletionRate())
                .scoreCompletion(view.score().getScoreCompletion())
                .weightedScore(view.score().getWeightedScore())
                .statusColor(view.score().getStatusColor())
                .achievementStatus(view.score().getAchievementStatus())
                .actionPlanCount(view.actionPlans().size())
                .taskSummary(calculationService.summarizeTasks(view.tasks(), today))
                .build();
    }

    private DashboardActionPlanResponse actionPlanResponse(
            ActionPlan plan, List<Task> tasks, LocalDate today, boolean includeTaskDetails) {
        DashboardTaskSummary summary = calculationService.summarizeTasks(tasks, today);
        return DashboardActionPlanResponse.builder()
                .actionPlanId(plan.getId())
                .departmentKpiId(plan.getDepartmentKpi().getId())
                .name(plan.getName())
                .ownerId(plan.getOwner().getId())
                .ownerName(plan.getOwner().getFullName())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .priority(plan.getPriority())
                .status(plan.getStatus())
                .progressPercent(summary.getWorkProgressPercent())
                .taskSummary(summary)
                .tasks(includeTaskDetails ? tasks.stream().map(task -> DashboardTaskDetailResponse.builder()
                        .taskId(task.getId())
                        .name(task.getName())
                        .assigneeId(task.getAssignee().getId())
                        .assigneeName(task.getAssignee().getFullName())
                        .startDate(task.getStartDate())
                        .dueDate(task.getDueDate())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .blockReason(task.getBlockReason())
                        .overdue(calculationService.isOverdue(task, today))
                        .build()).toList() : null)
                .build();
    }

    private DashboardMeasurementSummary measurementSummary(KpiMeasurement measurement) {
        if (measurement == null) {
            return null;
        }
        return DashboardMeasurementSummary.builder()
                .unit(measurement.getUnit())
                .baselineValue(measurement.getBaselineValue())
                .targetValue(measurement.getTargetValue())
                .direction(measurement.getDirection())
                .reportingFrequency(measurement.getReportingFrequency())
                .formulaDescription(measurement.getFormulaDescription())
                .greenThreshold(measurement.getGreenThreshold())
                .yellowThreshold(measurement.getYellowThreshold())
                .redThreshold(measurement.getRedThreshold())
                .reportOwnerId(measurement.getReportOwner() == null ? null : measurement.getReportOwner().getId())
                .reportOwnerName(measurement.getReportOwner() == null ? null : measurement.getReportOwner().getFullName())
                .status(measurement.getStatus())
                .build();
    }

    private DashboardReportSummary reportSummary(KpiReport report, boolean selected) {
        if (report == null) {
            return null;
        }
        return DashboardReportSummary.builder()
                .reportId(report.getId())
                .reportingPeriod(report.getReportingPeriod())
                .actualValue(report.getActualValue())
                .reviewStatus(report.getReviewStatus())
                .reporterId(report.getReporter() == null ? null : report.getReporter().getId())
                .reporterName(report.getReporter() == null ? null : report.getReporter().getFullName())
                .submittedAt(report.getSubmittedAt())
                .reviewedAt(report.getReviewedAt())
                .createdAt(report.getCreatedAt())
                .note(report.getNote())
                .evidenceUrl(report.getEvidenceUrl())
                .selectedForScoring(selected)
                .build();
    }

    private DashboardKpiSummary summarizeKpis(List<KpiView> kpis) {
        return DashboardKpiSummary.builder()
                .total(kpis.size())
                .approved(countDataStatus(kpis, DashboardScoringDataStatus.APPROVED))
                .provisional(countDataStatus(kpis, DashboardScoringDataStatus.PROVISIONAL))
                .missingReport(countDataStatus(kpis, DashboardScoringDataStatus.MISSING_REPORT))
                .missingMeasurement(countDataStatus(kpis, DashboardScoringDataStatus.MISSING_MEASUREMENT))
                .green(kpis.stream().filter(kpi -> kpi.score().getStatusColor() == KpiStatusColor.GREEN).count())
                .yellow(kpis.stream().filter(kpi -> kpi.score().getStatusColor() == KpiStatusColor.YELLOW).count())
                .red(kpis.stream().filter(kpi -> kpi.score().getStatusColor() == KpiStatusColor.RED).count())
                .inProgress(kpis.stream().filter(kpi -> kpi.score().getAchievementStatus() == KpiAchievementStatus.IN_PROGRESS).count())
                .achieved(kpis.stream().filter(kpi -> kpi.score().getAchievementStatus() == KpiAchievementStatus.ACHIEVED).count())
                .exceeded(kpis.stream().filter(kpi -> kpi.score().getAchievementStatus() == KpiAchievementStatus.EXCEEDED).count())
                .build();
    }

    private DashboardStrategySummary strategySummary(BscStrategy strategy) {
        return DashboardStrategySummary.builder()
                .strategyId(strategy.getId())
                .strategyName(strategy.getName())
                .year(strategy.getYear())
                .strategyStatus(strategy.getStatus())
                .companyId(strategy.getCompany().getId())
                .companyName(strategy.getCompany().getName())
                .build();
    }

    private DepartmentDashboardResponse.DepartmentSummary departmentSummary(Department department) {
        return DepartmentDashboardResponse.DepartmentSummary.builder()
                .departmentId(department.getId())
                .companyId(department.getCompany().getId())
                .name(department.getName())
                .code(department.getCode())
                .color(department.getColor())
                .status(department.getStatus())
                .build();
    }

    private BscStrategy getStrategy(UUID strategyId) {
        return bscStrategyRepository.findById(strategyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BSC_STRATEGY_NOT_FOUND));
    }

    private List<KpiView> kpisForObjective(Snapshot snapshot, UUID objectiveId) {
        return snapshot.kpis().stream()
                .filter(kpi -> kpi.kpi().getFinalStrategicObjective().getId().equals(objectiveId))
                .toList();
    }

    private List<KpiView> kpisForDepartment(Snapshot snapshot, UUID departmentId) {
        return snapshot.kpis().stream()
                .filter(kpi -> kpi.kpi().getDepartment().getId().equals(departmentId))
                .toList();
    }

    private List<Task> tasksForKpis(Snapshot snapshot, List<KpiView> kpis) {
        Set<UUID> ids = kpis.stream().map(kpi -> kpi.kpi().getId()).collect(Collectors.toSet());
        return snapshot.tasks().stream().filter(task -> ids.contains(task.getDepartmentKpi().getId())).toList();
    }

    private List<ActionPlan> plansForKpis(Snapshot snapshot, List<KpiView> kpis) {
        Set<UUID> ids = kpis.stream().map(kpi -> kpi.kpi().getId()).collect(Collectors.toSet());
        return snapshot.actionPlans().stream().filter(plan -> ids.contains(plan.getDepartmentKpi().getId())).toList();
    }

    private List<Task> tasksForPlan(Snapshot snapshot, UUID actionPlanId) {
        return snapshot.tasks().stream().filter(task -> task.getActionPlan().getId().equals(actionPlanId)).toList();
    }

    private BigDecimal sumScores(List<KpiView> kpis) {
        return calculationService.scale(kpis.stream()
                .map(kpi -> kpi.score().getWeightedScore())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private BigDecimal sumWeights(List<KpiView> kpis) {
        return calculationService.scale(kpis.stream()
                .map(KpiView::weight)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private long countDataStatus(List<KpiView> kpis, DashboardScoringDataStatus status) {
        return kpis.stream().filter(kpi -> kpi.score().getDataStatus() == status).count();
    }

    private record KpiView(
            DepartmentKpi kpi,
            BigDecimal weight,
            KpiMeasurement measurement,
            List<KpiReport> reports,
            DashboardCalculationService.ScoringReportSelection selection,
            DashboardCalculationService.KpiScore score,
            List<ActionPlan> actionPlans,
            List<Task> tasks
    ) {
    }

    private record Snapshot(
            BscStrategy strategy,
            List<FinalStrategicObjective> objectives,
            List<Department> departments,
            List<KpiView> kpis,
            Map<BscPerspectiveCode, BigDecimal> perspectiveWeights,
            Map<UUID, BigDecimal> objectiveWeights,
            List<ActionPlan> actionPlans,
            List<Task> tasks
    ) {
    }
}
