package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b8.dto.response.B8CompletionResponse;
import com.skillforge.bsc.bsc.b8.entity.ActionPlan;
import com.skillforge.bsc.bsc.b8.entity.KpiReport;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.repository.ActionPlanRepository;
import com.skillforge.bsc.bsc.b8.repository.KpiReportRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskRepository;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class B8CompletionService {

    private final B8ValidationService validationService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final ActionPlanRepository actionPlanRepository;
    private final TaskRepository taskRepository;
    private final KpiReportRepository kpiReportRepository;

    @Transactional
    public B8CompletionResponse complete(UUID strategyId) {
        validationService.getStrategy(strategyId);
        validationService.ensureB7Completed(strategyId);

        BscStepStatus b8Status = validationService.getStepStatus(strategyId, BscStepCode.B8_ACTION_PLAN);
        if (b8Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b8Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        List<ActionPlan> actionPlans = actionPlanRepository.findByBscStrategy_IdOrderByStartDateAscCreatedAtAsc(strategyId);
        if (actionPlans.isEmpty()) {
            throw new BusinessException(ErrorCode.B8_ACTION_PLAN_REQUIRED);
        }

        for (ActionPlan actionPlan : actionPlans) {
            KpiMeasurement measurement = validationService.getMeasuredKpi(strategyId, actionPlan.getDepartmentKpi().getId());
            validateActionPlanTrace(actionPlan, measurement);
            if (taskRepository.countByActionPlan_Id(actionPlan.getId()) == 0) {
                throw new BusinessException(ErrorCode.B8_TASK_REQUIRED);
            }
        }

        List<Task> tasks = taskRepository.findByBscStrategy_IdOrderByDueDateAscCreatedAtAsc(strategyId);
        if (tasks.isEmpty()) {
            throw new BusinessException(ErrorCode.B8_TASK_REQUIRED);
        }
        for (Task task : tasks) {
            validateTaskTrace(task);
        }

        for (KpiReport report : kpiReportRepository.findByBscStrategy_IdOrderByReportingPeriodDescCreatedAtDesc(strategyId)) {
            KpiMeasurement measurement = validationService.getMeasuredKpi(strategyId, report.getDepartmentKpi().getId());
            validateReportTrace(report, measurement);
        }

        b8Status.setStatus(BscStepStatusValue.COMPLETED);
        b8Status.setCompletedAt(LocalDateTime.now());
        b8Status.setCompletedBy(null);
        b8Status.setInvalidatedReason(null);
        bscStepStatusRepository.save(b8Status);

        return B8CompletionResponse.builder()
                .bscStrategyId(strategyId)
                .stepStatus(b8Status.getStatus())
                .actionPlanCount(actionPlans.size())
                .taskCount(tasks.size())
                .completedAt(b8Status.getCompletedAt())
                .build();
    }

    private void validateActionPlanTrace(ActionPlan actionPlan, KpiMeasurement measurement) {
        if (!actionPlan.getBscStrategy().getId().equals(measurement.getBscStrategy().getId())
                || !actionPlan.getDepartmentKpi().getId().equals(measurement.getDepartmentKpi().getId())
                || !actionPlan.getFinalStrategicObjective().getId().equals(measurement.getFinalStrategicObjective().getId())
                || !actionPlan.getDepartment().getId().equals(measurement.getDepartment().getId())) {
            throw new BusinessException(ErrorCode.B8_TRACEABILITY_INVALID);
        }
    }

    private void validateTaskTrace(Task task) {
        ActionPlan actionPlan = task.getActionPlan();
        if (!task.getBscStrategy().getId().equals(actionPlan.getBscStrategy().getId())
                || !task.getDepartmentKpi().getId().equals(actionPlan.getDepartmentKpi().getId())
                || !task.getFinalStrategicObjective().getId().equals(actionPlan.getFinalStrategicObjective().getId())
                || !task.getDepartment().getId().equals(actionPlan.getDepartment().getId())
                || !task.getAssignee().getDepartment().getId().equals(actionPlan.getDepartment().getId())) {
            throw new BusinessException(ErrorCode.B8_TRACEABILITY_INVALID);
        }
    }

    private void validateReportTrace(KpiReport report, KpiMeasurement measurement) {
        if (!report.getBscStrategy().getId().equals(measurement.getBscStrategy().getId())
                || !report.getDepartmentKpi().getId().equals(measurement.getDepartmentKpi().getId())
                || !report.getFinalStrategicObjective().getId().equals(measurement.getFinalStrategicObjective().getId())
                || !report.getDepartment().getId().equals(measurement.getDepartment().getId())) {
            throw new BusinessException(ErrorCode.B8_TRACEABILITY_INVALID);
        }
    }
}
