package com.skillforge.bsc.bsc.b8.mapper;

import com.skillforge.bsc.bsc.b8.dto.response.ActionPlanResponse;
import com.skillforge.bsc.bsc.b8.entity.ActionPlan;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ActionPlanMapper {

    public ActionPlanResponse toResponse(ActionPlan actionPlan, long taskCount, BigDecimal progressPercent) {
        return ActionPlanResponse.builder()
                .id(actionPlan.getId())
                .bscStrategyId(actionPlan.getBscStrategy().getId())
                .departmentKpiId(actionPlan.getDepartmentKpi().getId())
                .departmentKpiName(actionPlan.getDepartmentKpi().getName())
                .finalStrategicObjectiveId(actionPlan.getFinalStrategicObjective().getId())
                .finalStrategicObjectiveName(actionPlan.getFinalStrategicObjective().getName())
                .perspectiveCode(actionPlan.getFinalStrategicObjective().getPerspectiveCode())
                .departmentId(actionPlan.getDepartment().getId())
                .departmentName(actionPlan.getDepartment().getName())
                .departmentCode(actionPlan.getDepartment().getCode())
                .name(actionPlan.getName())
                .description(actionPlan.getDescription())
                .startDate(actionPlan.getStartDate())
                .endDate(actionPlan.getEndDate())
                .ownerId(actionPlan.getOwner().getId())
                .ownerName(actionPlan.getOwner().getFullName())
                .priority(actionPlan.getPriority())
                .status(actionPlan.getStatus())
                .taskCount(taskCount)
                .progressPercent(progressPercent)
                .createdBy(actionPlan.getCreatedBy())
                .updatedBy(actionPlan.getUpdatedBy())
                .createdAt(actionPlan.getCreatedAt())
                .updatedAt(actionPlan.getUpdatedAt())
                .build();
    }
}
