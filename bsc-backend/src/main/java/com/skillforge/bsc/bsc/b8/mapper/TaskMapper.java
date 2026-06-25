package com.skillforge.bsc.bsc.b8.mapper;

import com.skillforge.bsc.bsc.b8.dto.response.GanttDependencyResponse;
import com.skillforge.bsc.bsc.b8.dto.response.GanttTaskResponse;
import com.skillforge.bsc.bsc.b8.dto.response.TaskResponse;
import com.skillforge.bsc.bsc.b8.entity.Task;
import com.skillforge.bsc.bsc.b8.entity.TaskDependency;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task, boolean overdue) {
        return TaskResponse.builder()
                .id(task.getId())
                .bscStrategyId(task.getBscStrategy().getId())
                .actionPlanId(task.getActionPlan().getId())
                .actionPlanName(task.getActionPlan().getName())
                .departmentKpiId(task.getDepartmentKpi().getId())
                .departmentKpiName(task.getDepartmentKpi().getName())
                .finalStrategicObjectiveId(task.getFinalStrategicObjective().getId())
                .finalStrategicObjectiveName(task.getFinalStrategicObjective().getName())
                .departmentId(task.getDepartment().getId())
                .departmentName(task.getDepartment().getName())
                .departmentCode(task.getDepartment().getCode())
                .assigneeId(task.getAssignee().getId())
                .assigneeName(task.getAssignee().getFullName())
                .name(task.getName())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .progressPercent(task.getProgressPercent())
                .priority(task.getPriority())
                .blockReason(task.getBlockReason())
                .evidenceUrl(task.getEvidenceUrl())
                .overdue(overdue)
                .createdBy(task.getCreatedBy())
                .updatedBy(task.getUpdatedBy())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    public GanttTaskResponse toGanttTaskResponse(Task task, boolean overdue) {
        return GanttTaskResponse.builder()
                .id(task.getId())
                .actionPlanId(task.getActionPlan().getId())
                .departmentKpiId(task.getDepartmentKpi().getId())
                .departmentKpiName(task.getDepartmentKpi().getName())
                .departmentId(task.getDepartment().getId())
                .departmentName(task.getDepartment().getName())
                .assigneeId(task.getAssignee().getId())
                .assigneeName(task.getAssignee().getFullName())
                .name(task.getName())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .progressPercent(task.getProgressPercent())
                .priority(task.getPriority())
                .overdue(overdue)
                .build();
    }

    public GanttDependencyResponse toDependencyResponse(TaskDependency dependency) {
        return GanttDependencyResponse.builder()
                .id(dependency.getId())
                .sourceTaskId(dependency.getSourceTask().getId())
                .targetTaskId(dependency.getTargetTask().getId())
                .dependencyType(dependency.getDependencyType())
                .build();
    }
}
