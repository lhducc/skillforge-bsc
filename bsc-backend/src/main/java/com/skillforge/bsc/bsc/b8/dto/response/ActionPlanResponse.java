package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.ActionPlanStatus;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.TaskPriority;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ActionPlanResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID departmentKpiId;
    private String departmentKpiName;
    private UUID finalStrategicObjectiveId;
    private String finalStrategicObjectiveName;
    private BscPerspectiveCode perspectiveCode;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID ownerId;
    private String ownerName;
    private TaskPriority priority;
    private ActionPlanStatus status;
    private long taskCount;
    private BigDecimal progressPercent;
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
