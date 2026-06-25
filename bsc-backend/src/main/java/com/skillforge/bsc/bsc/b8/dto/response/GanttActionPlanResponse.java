package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.ActionPlanStatus;
import com.skillforge.bsc.common.enums.TaskPriority;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class GanttActionPlanResponse {

    private UUID id;
    private UUID departmentKpiId;
    private String departmentKpiName;
    private UUID departmentId;
    private String departmentName;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID ownerId;
    private String ownerName;
    private TaskPriority priority;
    private ActionPlanStatus status;
    private BigDecimal progressPercent;
    private List<GanttTaskResponse> tasks;
}
