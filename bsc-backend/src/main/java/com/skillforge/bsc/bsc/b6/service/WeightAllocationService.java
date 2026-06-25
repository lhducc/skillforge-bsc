package com.skillforge.bsc.bsc.b6.service;

import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b4.repository.FinalStrategicObjectiveRepository;
import com.skillforge.bsc.bsc.b5.entity.DepartmentKpi;
import com.skillforge.bsc.bsc.b5.repository.DepartmentKpiRepository;
import com.skillforge.bsc.bsc.b6.dto.request.KpiWeightItemRequest;
import com.skillforge.bsc.bsc.b6.dto.request.ObjectiveWeightItemRequest;
import com.skillforge.bsc.bsc.b6.dto.request.PerspectiveWeightItemRequest;
import com.skillforge.bsc.bsc.b6.dto.request.UpsertKpiWeightsRequest;
import com.skillforge.bsc.bsc.b6.dto.request.UpsertObjectiveWeightsRequest;
import com.skillforge.bsc.bsc.b6.dto.request.UpsertPerspectiveWeightsRequest;
import com.skillforge.bsc.bsc.b6.dto.response.KpiWeightResponse;
import com.skillforge.bsc.bsc.b6.dto.response.ObjectiveWeightResponse;
import com.skillforge.bsc.bsc.b6.dto.response.PerspectiveWeightResponse;
import com.skillforge.bsc.bsc.b6.dto.response.WeightTreeResponse;
import com.skillforge.bsc.bsc.b6.entity.KpiWeight;
import com.skillforge.bsc.bsc.b6.entity.ObjectiveWeight;
import com.skillforge.bsc.bsc.b6.entity.PerspectiveWeight;
import com.skillforge.bsc.bsc.b6.mapper.WeightAllocationMapper;
import com.skillforge.bsc.bsc.b6.repository.KpiWeightRepository;
import com.skillforge.bsc.bsc.b6.repository.ObjectiveWeightRepository;
import com.skillforge.bsc.bsc.b6.repository.PerspectiveWeightRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.enums.DepartmentKpiStatus;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightAllocationService {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.000");

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final FinalStrategicObjectiveRepository finalStrategicObjectiveRepository;
    private final DepartmentKpiRepository departmentKpiRepository;
    private final PerspectiveWeightRepository perspectiveWeightRepository;
    private final ObjectiveWeightRepository objectiveWeightRepository;
    private final KpiWeightRepository kpiWeightRepository;
    private final WeightAllocationMapper weightAllocationMapper;

    @Transactional
    public WeightTreeResponse upsertPerspectiveWeights(UUID strategyId, UpsertPerspectiveWeightsRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB5Completed(strategyId);
        ensureB6StepEditable(strategyId);

        Map<BscPerspectiveCode, BigDecimal> weightsByPerspective = validatePerspectiveRequest(request.getItems());

        perspectiveWeightRepository.deleteByBscStrategy_Id(strategyId);
        perspectiveWeightRepository.flush();
        perspectiveWeightRepository.saveAll(weightsByPerspective.entrySet()
                .stream()
                .map(entry -> toPerspectiveWeight(strategy, entry.getKey(), entry.getValue()))
                .toList());

        return buildWeightTree(strategyId);
    }

    @Transactional
    public WeightTreeResponse upsertObjectiveWeights(UUID strategyId, UpsertObjectiveWeightsRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB5Completed(strategyId);
        ensureB6StepEditable(strategyId);

        Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights = validatePersistedPerspectiveWeights(strategyId);
        List<FinalStrategicObjective> activeObjectives = activeFinalObjectives(strategyId);
        Map<UUID, ObjectiveWeightItemRequest> requestByObjectiveId = validateObjectiveRequest(request.getItems(), activeObjectives);
        validateObjectiveTotals(requestByObjectiveId, perspectiveWeights, activeObjectives);

        objectiveWeightRepository.deleteByBscStrategy_Id(strategyId);
        objectiveWeightRepository.flush();
        objectiveWeightRepository.saveAll(activeObjectives.stream()
                .map(objective -> toObjectiveWeight(strategy, objective, requestByObjectiveId.get(objective.getId()).getWeightPercent()))
                .toList());

        return buildWeightTree(strategyId);
    }

    @Transactional
    public WeightTreeResponse upsertKpiWeights(UUID strategyId, UpsertKpiWeightsRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB5Completed(strategyId);
        ensureB6StepEditable(strategyId);

        Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights = validatePersistedPerspectiveWeights(strategyId);
        List<FinalStrategicObjective> activeObjectives = activeFinalObjectives(strategyId);
        Map<UUID, ObjectiveWeight> objectiveWeights = validatePersistedObjectiveWeights(strategyId, perspectiveWeights, activeObjectives);
        List<DepartmentKpi> activeKpis = activeDepartmentKpis(strategyId);
        Map<UUID, KpiWeightItemRequest> requestByKpiId = validateKpiRequest(request.getItems(), activeKpis);
        validateKpiTotals(requestByKpiId, objectiveWeights, activeKpis);

        kpiWeightRepository.deleteByBscStrategy_Id(strategyId);
        kpiWeightRepository.flush();
        kpiWeightRepository.saveAll(activeKpis.stream()
                .map(kpi -> toKpiWeight(strategy, kpi, requestByKpiId.get(kpi.getId()).getWeightPercent()))
                .toList());

        return buildWeightTree(strategyId);
    }

    @Transactional(readOnly = true)
    public WeightTreeResponse getWeightTree(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        return buildWeightTree(strategyId);
    }

    @Transactional
    public WeightTreeResponse complete(UUID strategyId) {
        getEditableStrategy(strategyId);
        ensureB5Completed(strategyId);

        BscStepStatus b6Status = getStepStatus(strategyId, BscStepCode.B6_WEIGHT_ALLOCATION);
        BscStepStatus b7Status = getStepStatus(strategyId, BscStepCode.B7_MEASUREMENT_TARGET);
        if (b6Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b6Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights = validatePersistedPerspectiveWeights(strategyId);
        List<FinalStrategicObjective> activeObjectives = activeFinalObjectives(strategyId);
        Map<UUID, ObjectiveWeight> objectiveWeights = validatePersistedObjectiveWeights(strategyId, perspectiveWeights, activeObjectives);
        validatePersistedKpiWeights(strategyId, objectiveWeights, activeDepartmentKpis(strategyId));

        b6Status.setStatus(BscStepStatusValue.COMPLETED);
        b6Status.setCompletedAt(LocalDateTime.now());
        b6Status.setCompletedBy(null);
        b6Status.setInvalidatedReason(null);

        if (b7Status.getStatus() == BscStepStatusValue.LOCKED) {
            b7Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b6Status, b7Status));
        return buildWeightTree(strategyId);
    }

    private WeightTreeResponse buildWeightTree(UUID strategyId) {
        Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights = perspectiveWeightRepository
                .findByBscStrategy_Id(strategyId)
                .stream()
                .collect(Collectors.toMap(PerspectiveWeight::getPerspectiveCode, weight -> weight));
        Map<UUID, ObjectiveWeight> objectiveWeights = objectiveWeightRepository.findByBscStrategy_Id(strategyId)
                .stream()
                .collect(Collectors.toMap(weight -> weight.getFinalStrategicObjective().getId(), weight -> weight));
        Map<UUID, KpiWeight> kpiWeights = kpiWeightRepository.findByBscStrategy_Id(strategyId)
                .stream()
                .collect(Collectors.toMap(weight -> weight.getDepartmentKpi().getId(), weight -> weight));

        List<FinalStrategicObjective> finalObjectives = activeFinalObjectives(strategyId);
        List<DepartmentKpi> kpis = activeDepartmentKpis(strategyId);
        Map<BscPerspectiveCode, List<FinalStrategicObjective>> objectivesByPerspective = finalObjectives.stream()
                .collect(Collectors.groupingBy(
                        FinalStrategicObjective::getPerspectiveCode,
                        () -> new EnumMap<>(BscPerspectiveCode.class),
                        Collectors.toList()
                ));
        Map<UUID, List<DepartmentKpi>> kpisByObjective = kpis.stream()
                .collect(Collectors.groupingBy(kpi -> kpi.getFinalStrategicObjective().getId()));

        List<PerspectiveWeightResponse> perspectiveResponses = Arrays.stream(BscPerspectiveCode.values())
                .map(perspectiveCode -> toPerspectiveTree(
                        perspectiveCode,
                        perspectiveWeights.get(perspectiveCode),
                        objectivesByPerspective.getOrDefault(perspectiveCode, List.of()),
                        objectiveWeights,
                        kpisByObjective,
                        kpiWeights
                ))
                .toList();

        BigDecimal allocatedPerspectiveWeight = perspectiveWeights.values()
                .stream()
                .map(PerspectiveWeight::getWeightPercent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        boolean valid = allocatedPerspectiveWeight.compareTo(ONE_HUNDRED) == 0
                && perspectiveResponses.stream().allMatch(PerspectiveWeightResponse::isValid);

        return WeightTreeResponse.builder()
                .bscStrategyId(strategyId)
                .totalWeightPercent(ONE_HUNDRED)
                .allocatedPerspectiveWeightPercent(allocatedPerspectiveWeight)
                .remainingPerspectiveWeightPercent(ONE_HUNDRED.subtract(allocatedPerspectiveWeight))
                .valid(valid)
                .perspectives(perspectiveResponses)
                .build();
    }

    private PerspectiveWeightResponse toPerspectiveTree(
            BscPerspectiveCode perspectiveCode,
            PerspectiveWeight perspectiveWeight,
            List<FinalStrategicObjective> objectives,
            Map<UUID, ObjectiveWeight> objectiveWeights,
            Map<UUID, List<DepartmentKpi>> kpisByObjective,
            Map<UUID, KpiWeight> kpiWeights
    ) {
        List<ObjectiveWeightResponse> objectiveResponses = objectives.stream()
                .map(objective -> toObjectiveTree(
                        objective,
                        objectiveWeights.get(objective.getId()),
                        kpisByObjective.getOrDefault(objective.getId(), List.of()),
                        kpiWeights
                ))
                .toList();

        BigDecimal allocatedObjectiveWeight = objectiveResponses.stream()
                .map(ObjectiveWeightResponse::getWeightPercent)
                .filter(weight -> weight != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal perspectiveWeightPercent = perspectiveWeight == null ? null : perspectiveWeight.getWeightPercent();
        BigDecimal remainingObjectiveWeight = perspectiveWeightPercent == null
                ? null
                : perspectiveWeightPercent.subtract(allocatedObjectiveWeight);
        boolean valid = perspectiveWeightPercent != null
                && perspectiveWeightPercent.compareTo(BigDecimal.ZERO) > 0
                && allocatedObjectiveWeight.compareTo(perspectiveWeightPercent) == 0
                && objectiveResponses.stream().allMatch(ObjectiveWeightResponse::isValid);

        return weightAllocationMapper.toPerspectiveResponse(
                perspectiveCode,
                perspectiveWeightPercent,
                allocatedObjectiveWeight,
                remainingObjectiveWeight,
                valid,
                objectiveResponses
        );
    }

    private ObjectiveWeightResponse toObjectiveTree(
            FinalStrategicObjective objective,
            ObjectiveWeight objectiveWeight,
            List<DepartmentKpi> kpis,
            Map<UUID, KpiWeight> kpiWeights
    ) {
        List<KpiWeightResponse> kpiResponses = kpis.stream()
                .map(kpi -> {
                    KpiWeight kpiWeight = kpiWeights.get(kpi.getId());
                    return weightAllocationMapper.toKpiResponse(kpi, kpiWeight == null ? null : kpiWeight.getWeightPercent());
                })
                .toList();

        BigDecimal allocatedKpiWeight = kpiResponses.stream()
                .map(KpiWeightResponse::getWeightPercent)
                .filter(weight -> weight != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal objectiveWeightPercent = objectiveWeight == null ? null : objectiveWeight.getWeightPercent();
        BigDecimal remainingKpiWeight = objectiveWeightPercent == null
                ? null
                : objectiveWeightPercent.subtract(allocatedKpiWeight);
        boolean valid = objectiveWeightPercent != null
                && objectiveWeightPercent.compareTo(BigDecimal.ZERO) > 0
                && allocatedKpiWeight.compareTo(objectiveWeightPercent) == 0
                && kpiResponses.stream().allMatch(kpi -> kpi.getWeightPercent() != null
                        && kpi.getWeightPercent().compareTo(BigDecimal.ZERO) > 0);

        return weightAllocationMapper.toObjectiveResponse(
                objective,
                objectiveWeightPercent,
                allocatedKpiWeight,
                remainingKpiWeight,
                valid,
                kpiResponses
        );
    }

    private Map<BscPerspectiveCode, BigDecimal> validatePerspectiveRequest(List<PerspectiveWeightItemRequest> items) {
        if (items == null) {
            throw new BusinessException(ErrorCode.B6_PERSPECTIVE_WEIGHT_MISSING);
        }
        Map<BscPerspectiveCode, BigDecimal> weightsByPerspective = new EnumMap<>(BscPerspectiveCode.class);
        for (PerspectiveWeightItemRequest item : items) {
            if (item == null || item.getPerspectiveCode() == null) {
                throw new BusinessException(ErrorCode.B6_PERSPECTIVE_WEIGHT_MISSING);
            }
            validateWeight(item.getWeightPercent());
            if (weightsByPerspective.put(item.getPerspectiveCode(), item.getWeightPercent()) != null) {
                throw new BusinessException(ErrorCode.B6_PERSPECTIVE_WEIGHT_MISSING, "Each perspective must appear exactly once");
            }
        }
        if (!weightsByPerspective.keySet().equals(EnumSet.allOf(BscPerspectiveCode.class))) {
            throw new BusinessException(ErrorCode.B6_PERSPECTIVE_WEIGHT_MISSING);
        }
        BigDecimal total = sum(weightsByPerspective.values().stream().toList());
        if (total.compareTo(ONE_HUNDRED) != 0) {
            throw new BusinessException(ErrorCode.B6_TOTAL_PERSPECTIVE_WEIGHT_INVALID);
        }
        return weightsByPerspective;
    }

    private Map<UUID, ObjectiveWeightItemRequest> validateObjectiveRequest(
            List<ObjectiveWeightItemRequest> items,
            List<FinalStrategicObjective> activeObjectives
    ) {
        if (items == null) {
            throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING);
        }
        Map<UUID, FinalStrategicObjective> activeObjectivesById = activeObjectives.stream()
                .collect(Collectors.toMap(FinalStrategicObjective::getId, objective -> objective));
        Map<UUID, ObjectiveWeightItemRequest> requestByObjectiveId = new HashMap<>();
        for (ObjectiveWeightItemRequest item : items) {
            if (item == null || item.getFinalStrategicObjectiveId() == null || item.getPerspectiveCode() == null) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING);
            }
            validateWeight(item.getWeightPercent());
            FinalStrategicObjective objective = activeObjectivesById.get(item.getFinalStrategicObjectiveId());
            if (objective == null) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING, "Objective must belong to current BSC strategy");
            }
            if (objective.getPerspectiveCode() != item.getPerspectiveCode()) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING, "Objective perspective does not match");
            }
            if (requestByObjectiveId.put(item.getFinalStrategicObjectiveId(), item) != null) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING, "Each objective must appear exactly once");
            }
        }
        if (!requestByObjectiveId.keySet().equals(activeObjectivesById.keySet())) {
            throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING);
        }
        return requestByObjectiveId;
    }

    private Map<UUID, KpiWeightItemRequest> validateKpiRequest(
            List<KpiWeightItemRequest> items,
            List<DepartmentKpi> activeKpis
    ) {
        if (items == null) {
            throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING);
        }
        Map<UUID, DepartmentKpi> activeKpisById = activeKpis.stream()
                .collect(Collectors.toMap(DepartmentKpi::getId, kpi -> kpi));
        Map<UUID, KpiWeightItemRequest> requestByKpiId = new HashMap<>();
        for (KpiWeightItemRequest item : items) {
            if (item == null
                    || item.getDepartmentKpiId() == null
                    || item.getFinalStrategicObjectiveId() == null
                    || item.getDepartmentId() == null
                    || item.getPerspectiveCode() == null) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING);
            }
            validateWeight(item.getWeightPercent());
            DepartmentKpi kpi = activeKpisById.get(item.getDepartmentKpiId());
            if (kpi == null) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING, "Department KPI must belong to current BSC strategy");
            }
            if (!kpi.getFinalStrategicObjective().getId().equals(item.getFinalStrategicObjectiveId())
                    || !kpi.getDepartment().getId().equals(item.getDepartmentId())
                    || kpi.getFinalStrategicObjective().getPerspectiveCode() != item.getPerspectiveCode()) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING, "Department KPI relationship does not match request");
            }
            if (requestByKpiId.put(item.getDepartmentKpiId(), item) != null) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING, "Each department KPI must appear exactly once");
            }
        }
        if (!requestByKpiId.keySet().equals(activeKpisById.keySet())) {
            throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING);
        }
        return requestByKpiId;
    }

    private Map<BscPerspectiveCode, PerspectiveWeight> validatePersistedPerspectiveWeights(UUID strategyId) {
        List<PerspectiveWeight> weights = perspectiveWeightRepository.findByBscStrategy_Id(strategyId);
        Map<BscPerspectiveCode, PerspectiveWeight> weightsByPerspective = new EnumMap<>(BscPerspectiveCode.class);
        for (PerspectiveWeight weight : weights) {
            validateWeight(weight.getWeightPercent());
            if (weightsByPerspective.put(weight.getPerspectiveCode(), weight) != null) {
                throw new BusinessException(ErrorCode.B6_PERSPECTIVE_WEIGHT_MISSING);
            }
        }
        if (!weightsByPerspective.keySet().equals(EnumSet.allOf(BscPerspectiveCode.class))) {
            throw new BusinessException(ErrorCode.B6_PERSPECTIVE_WEIGHT_MISSING);
        }
        BigDecimal total = weightsByPerspective.values()
                .stream()
                .map(PerspectiveWeight::getWeightPercent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(ONE_HUNDRED) != 0) {
            throw new BusinessException(ErrorCode.B6_TOTAL_PERSPECTIVE_WEIGHT_INVALID);
        }
        return weightsByPerspective;
    }

    private Map<UUID, ObjectiveWeight> validatePersistedObjectiveWeights(
            UUID strategyId,
            Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights,
            List<FinalStrategicObjective> activeObjectives
    ) {
        Map<UUID, FinalStrategicObjective> activeObjectivesById = activeObjectives.stream()
                .collect(Collectors.toMap(FinalStrategicObjective::getId, objective -> objective));
        Map<UUID, ObjectiveWeight> weightsByObjective = new HashMap<>();
        for (ObjectiveWeight weight : objectiveWeightRepository.findByBscStrategy_Id(strategyId)) {
            validateWeight(weight.getWeightPercent());
            FinalStrategicObjective objective = activeObjectivesById.get(weight.getFinalStrategicObjective().getId());
            if (objective == null || objective.getPerspectiveCode() != weight.getPerspectiveCode()) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING);
            }
            if (weightsByObjective.put(objective.getId(), weight) != null) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING);
            }
        }
        if (!weightsByObjective.keySet().equals(activeObjectivesById.keySet())) {
            throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_MISSING);
        }

        validateObjectiveEntityTotals(weightsByObjective, perspectiveWeights, activeObjectives);
        return weightsByObjective;
    }

    private void validatePersistedKpiWeights(
            UUID strategyId,
            Map<UUID, ObjectiveWeight> objectiveWeights,
            List<DepartmentKpi> activeKpis
    ) {
        Map<UUID, DepartmentKpi> activeKpisById = activeKpis.stream()
                .collect(Collectors.toMap(DepartmentKpi::getId, kpi -> kpi));
        Map<UUID, KpiWeight> weightsByKpi = new HashMap<>();
        for (KpiWeight weight : kpiWeightRepository.findByBscStrategy_Id(strategyId)) {
            validateWeight(weight.getWeightPercent());
            DepartmentKpi kpi = activeKpisById.get(weight.getDepartmentKpi().getId());
            if (kpi == null
                    || !kpi.getFinalStrategicObjective().getId().equals(weight.getFinalStrategicObjective().getId())
                    || !kpi.getDepartment().getId().equals(weight.getDepartment().getId())
                    || kpi.getFinalStrategicObjective().getPerspectiveCode() != weight.getPerspectiveCode()) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING);
            }
            if (weightsByKpi.put(kpi.getId(), weight) != null) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING);
            }
        }
        if (!weightsByKpi.keySet().equals(activeKpisById.keySet())) {
            throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_MISSING);
        }

        validateKpiEntityTotals(weightsByKpi, objectiveWeights, activeKpis);
    }

    private void validateObjectiveTotals(
            Map<UUID, ObjectiveWeightItemRequest> requestByObjectiveId,
            Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights,
            List<FinalStrategicObjective> activeObjectives
    ) {
        Map<BscPerspectiveCode, BigDecimal> totalsByPerspective = new EnumMap<>(BscPerspectiveCode.class);
        for (FinalStrategicObjective objective : activeObjectives) {
            BigDecimal weight = requestByObjectiveId.get(objective.getId()).getWeightPercent();
            totalsByPerspective.merge(objective.getPerspectiveCode(), weight, BigDecimal::add);
        }
        for (BscPerspectiveCode perspectiveCode : BscPerspectiveCode.values()) {
            BigDecimal expected = perspectiveWeights.get(perspectiveCode).getWeightPercent();
            BigDecimal actual = totalsByPerspective.getOrDefault(perspectiveCode, BigDecimal.ZERO);
            if (actual.compareTo(expected) != 0) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_TOTAL_INVALID);
            }
        }
    }

    private void validateObjectiveEntityTotals(
            Map<UUID, ObjectiveWeight> weightsByObjective,
            Map<BscPerspectiveCode, PerspectiveWeight> perspectiveWeights,
            List<FinalStrategicObjective> activeObjectives
    ) {
        Map<BscPerspectiveCode, BigDecimal> totalsByPerspective = new EnumMap<>(BscPerspectiveCode.class);
        for (FinalStrategicObjective objective : activeObjectives) {
            BigDecimal weight = weightsByObjective.get(objective.getId()).getWeightPercent();
            totalsByPerspective.merge(objective.getPerspectiveCode(), weight, BigDecimal::add);
        }
        for (BscPerspectiveCode perspectiveCode : BscPerspectiveCode.values()) {
            BigDecimal expected = perspectiveWeights.get(perspectiveCode).getWeightPercent();
            BigDecimal actual = totalsByPerspective.getOrDefault(perspectiveCode, BigDecimal.ZERO);
            if (actual.compareTo(expected) != 0) {
                throw new BusinessException(ErrorCode.B6_OBJECTIVE_WEIGHT_TOTAL_INVALID);
            }
        }
    }

    private void validateKpiTotals(
            Map<UUID, KpiWeightItemRequest> requestByKpiId,
            Map<UUID, ObjectiveWeight> objectiveWeights,
            List<DepartmentKpi> activeKpis
    ) {
        Map<UUID, BigDecimal> totalsByObjective = new HashMap<>();
        Set<UUID> objectiveIdsWithKpis = new HashSet<>();
        for (DepartmentKpi kpi : activeKpis) {
            UUID objectiveId = kpi.getFinalStrategicObjective().getId();
            objectiveIdsWithKpis.add(objectiveId);
            BigDecimal weight = requestByKpiId.get(kpi.getId()).getWeightPercent();
            totalsByObjective.merge(objectiveId, weight, BigDecimal::add);
        }
        for (UUID objectiveId : objectiveIdsWithKpis) {
            ObjectiveWeight objectiveWeight = objectiveWeights.get(objectiveId);
            if (objectiveWeight == null || totalsByObjective.getOrDefault(objectiveId, BigDecimal.ZERO)
                    .compareTo(objectiveWeight.getWeightPercent()) != 0) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_TOTAL_INVALID);
            }
        }
    }

    private void validateKpiEntityTotals(
            Map<UUID, KpiWeight> weightsByKpi,
            Map<UUID, ObjectiveWeight> objectiveWeights,
            List<DepartmentKpi> activeKpis
    ) {
        Map<UUID, BigDecimal> totalsByObjective = new HashMap<>();
        Set<UUID> objectiveIdsWithKpis = new HashSet<>();
        for (DepartmentKpi kpi : activeKpis) {
            UUID objectiveId = kpi.getFinalStrategicObjective().getId();
            objectiveIdsWithKpis.add(objectiveId);
            BigDecimal weight = weightsByKpi.get(kpi.getId()).getWeightPercent();
            totalsByObjective.merge(objectiveId, weight, BigDecimal::add);
        }
        for (UUID objectiveId : objectiveIdsWithKpis) {
            ObjectiveWeight objectiveWeight = objectiveWeights.get(objectiveId);
            if (objectiveWeight == null || totalsByObjective.getOrDefault(objectiveId, BigDecimal.ZERO)
                    .compareTo(objectiveWeight.getWeightPercent()) != 0) {
                throw new BusinessException(ErrorCode.B6_KPI_WEIGHT_TOTAL_INVALID);
            }
        }
    }

    private PerspectiveWeight toPerspectiveWeight(BscStrategy strategy, BscPerspectiveCode perspectiveCode, BigDecimal weightPercent) {
        PerspectiveWeight weight = new PerspectiveWeight();
        weight.setBscStrategy(strategy);
        weight.setPerspectiveCode(perspectiveCode);
        weight.setWeightPercent(weightPercent);
        weight.setCreatedBy(null);
        weight.setUpdatedBy(null);
        return weight;
    }

    private ObjectiveWeight toObjectiveWeight(BscStrategy strategy, FinalStrategicObjective objective, BigDecimal weightPercent) {
        ObjectiveWeight weight = new ObjectiveWeight();
        weight.setBscStrategy(strategy);
        weight.setFinalStrategicObjective(objective);
        weight.setPerspectiveCode(objective.getPerspectiveCode());
        weight.setWeightPercent(weightPercent);
        weight.setCreatedBy(null);
        weight.setUpdatedBy(null);
        return weight;
    }

    private KpiWeight toKpiWeight(BscStrategy strategy, DepartmentKpi kpi, BigDecimal weightPercent) {
        KpiWeight weight = new KpiWeight();
        weight.setBscStrategy(strategy);
        weight.setDepartmentKpi(kpi);
        weight.setFinalStrategicObjective(kpi.getFinalStrategicObjective());
        weight.setDepartment(kpi.getDepartment());
        weight.setPerspectiveCode(kpi.getFinalStrategicObjective().getPerspectiveCode());
        weight.setWeightPercent(weightPercent);
        weight.setCreatedBy(null);
        weight.setUpdatedBy(null);
        return weight;
    }

    private List<FinalStrategicObjective> activeFinalObjectives(UUID strategyId) {
        return finalStrategicObjectiveRepository
                .findByBscStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                        strategyId,
                        StrategicObjectiveStatus.ACTIVE
                );
    }

    private List<DepartmentKpi> activeDepartmentKpis(UUID strategyId) {
        return departmentKpiRepository
                .findByBscStrategy_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(
                        strategyId,
                        DepartmentKpiStatus.ACTIVE
                );
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private void ensureB5Completed(UUID strategyId) {
        BscStepStatus b5Status = getStepStatus(strategyId, BscStepCode.B5_FISHBONE);
        if (b5Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B5_FISHBONE must be completed before B6");
        }
    }

    private void ensureB6StepEditable(UUID strategyId) {
        BscStepStatus b6Status = getStepStatus(strategyId, BscStepCode.B6_WEIGHT_ALLOCATION);
        if (b6Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b6Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private void validateWeight(BigDecimal weightPercent) {
        if (weightPercent == null || weightPercent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.B6_WEIGHT_MUST_BE_POSITIVE);
        }
        if (weightPercent.compareTo(ONE_HUNDRED) > 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "weightPercent must be less than or equal to 100");
        }
    }

    private BigDecimal sum(List<BigDecimal> values) {
        return values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
