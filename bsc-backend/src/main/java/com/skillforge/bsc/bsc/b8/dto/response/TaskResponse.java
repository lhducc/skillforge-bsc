package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.TaskPriority;
import com.skillforge.bsc.common.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TaskResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID actionPlanId;
    private String actionPlanName;
    private UUID departmentKpiId;
    private String departmentKpiName;
    private UUID finalStrategicObjectiveId;
    private String finalStrategicObjectiveName;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private UUID assigneeId;
    private String assigneeName;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private TaskStatus status;
    private BigDecimal progressPercent;
    private TaskPriority priority;
    private String blockReason;
    private String evidenceUrl;
    private boolean overdue;
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
