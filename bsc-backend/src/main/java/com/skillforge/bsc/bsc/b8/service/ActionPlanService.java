package com.skillforge.bsc.bsc.b8.service;

import com.skillforge.bsc.bsc.b7.entity.KpiMeasurement;
import com.skillforge.bsc.bsc.b8.dto.request.CreateActionPlanRequest;
import com.skillforge.bsc.bsc.b8.dto.request.UpdateActionPlanRequest;
import com.skillforge.bsc.bsc.b8.dto.response.ActionPlanResponse;
import com.skillforge.bsc.bsc.b8.entity.ActionPlan;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.mapper.ActionPlanMapper;
import com.skillforge.bsc.bsc.b8.repository.ActionPlanRepository;
import com.skillforge.bsc.bsc.b8.repository.TaskRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.common.enums.ActionPlanStatus;
import com.skillforge.bsc.common.enums.TaskStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.user.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionPlanService {

    private static final BigDecimal HUNDRED = new BigDecimal("100.000");
    private static final BigDecimal ZERO = new BigDecimal("0.000");

    private final B8ValidationService validationService;
    private final ActionPlanRepository actionPlanRepository;
    private final TaskRepository taskRepository;
    private final ActionPlanMapper actionPlanMapper;

    @Transactional
    public ActionPlanResponse create(CreateActionPlanRequest request) {
        BscStrategy strategy = validationService.getStrategy(request.getBscStrategyId());
        validationService.ensureB8Accessible(strategy.getId());
        KpiMeasurement measurement = validationService.getMeasuredKpi(strategy.getId(), request.getDepartmentKpiId());

        String name = validationService.normalizeRequired(
                request.getName(),
                ErrorCode.B8_ACTION_PLAN_NAME_REQUIRED,
                "Action plan name must not be blank"
        );
        validateDateRange(request.getStartDate(), request.getEndDate());
        Employee owner = validationService.getValidEmployeeInDepartment(
                strategy,
                measurement.getDepartment(),
                request.getOwnerId(),
                ErrorCode.B8_ACTION_PLAN_OWNER_INVALID
        );

        ActionPlan actionPlan = new ActionPlan();
        actionPlan.setBscStrategy(strategy);
        actionPlan.setDepartmentKpi(measurement.getDepartmentKpi());
        actionPlan.setFinalStrategicObjective(measurement.getFinalStrategicObjective());
        actionPlan.setDepartment(measurement.getDepartment());
        actionPlan.setName(name);
        actionPlan.setDescription(validationService.normalize(request.getDescription()));
        actionPlan.setStartDate(request.getStartDate());
        actionPlan.setEndDate(request.getEndDate());
        actionPlan.setOwner(owner);
        actionPlan.setPriority(request.getPriority());
        actionPlan.setStatus(request.getStatus());
        actionPlan.setCreatedBy(null);
        actionPlan.setUpdatedBy(null);

        return toResponse(actionPlanRepository.save(actionPlan));
    }

    @Transactional
    public ActionPlanResponse update(UUID actionPlanId, UpdateActionPlanRequest request) {
        ActionPlan actionPlan = getActionPlan(actionPlanId);
        validationService.ensureB8Accessible(actionPlan.getBscStrategy().getId());

        String name = validationService.normalizeRequired(
                request.getName(),
                ErrorCode.B8_ACTION_PLAN_NAME_REQUIRED,
                "Action plan name must not be blank"
        );
        validateDateRange(request.getStartDate(), request.getEndDate());
        Employee owner = validationService.getValidEmployeeInDepartment(
                actionPlan.getBscStrategy(),
                actionPlan.getDepartment(),
                request.getOwnerId(),
                ErrorCode.B8_ACTION_PLAN_OWNER_INVALID
        );
        validationService.getMeasuredKpi(actionPlan.getBscStrategy().getId(), actionPlan.getDepartmentKpi().getId());

        actionPlan.setName(name);
        actionPlan.setDescription(validationService.normalize(request.getDescription()));
        actionPlan.setStartDate(request.getStartDate());
        actionPlan.setEndDate(request.getEndDate());
        actionPlan.setOwner(owner);
        actionPlan.setPriority(request.getPriority());
        actionPlan.setStatus(request.getStatus());
        actionPlan.setUpdatedBy(null);

        return toResponse(actionPlanRepository.save(actionPlan));
    }

    @Transactional(readOnly = true)
    public List<ActionPlanResponse> list(
            UUID strategyId,
            UUID departmentKpiId,
            UUID departmentId,
            UUID ownerId,
            ActionPlanStatus status
    ) {
        validationService.getStrategy(strategyId);
        return actionPlanRepository.findByBscStrategy_IdOrderByStartDateAscCreatedAtAsc(strategyId)
                .stream()
                .filter(actionPlan -> departmentKpiId == null || actionPlan.getDepartmentKpi().getId().equals(departmentKpiId))
                .filter(actionPlan -> departmentId == null || actionPlan.getDepartment().getId().equals(departmentId))
                .filter(actionPlan -> ownerId == null || actionPlan.getOwner().getId().equals(ownerId))
                .filter(actionPlan -> status == null || actionPlan.getStatus() == status)
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ActionPlan getActionPlan(UUID actionPlanId) {
        return actionPlanRepository.findById(actionPlanId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B8_ACTION_PLAN_NOT_FOUND));
    }

    private ActionPlanResponse toResponse(ActionPlan actionPlan) {
        List<Task> tasks = taskRepository.findByActionPlan_IdOrderByDueDateAscCreatedAtAsc(actionPlan.getId());
        return actionPlanMapper.toResponse(actionPlan, tasks.size(), calculateProgress(tasks));
    }

    private BigDecimal calculateProgress(List<Task> tasks) {
        List<Task> validTasks = tasks.stream()
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED)
                .toList();
        if (validTasks.isEmpty()) {
            return ZERO;
        }
        long doneCount = validTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .count();
        return BigDecimal.valueOf(doneCount)
                .multiply(HUNDRED)
                .divide(BigDecimal.valueOf(validTasks.size()), 3, RoundingMode.HALF_UP);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new BusinessException(ErrorCode.B8_ACTION_PLAN_DATE_INVALID);
        }
    }
}
