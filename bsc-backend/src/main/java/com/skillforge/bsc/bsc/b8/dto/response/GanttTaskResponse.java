package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.TaskPriority;
import com.skillforge.bsc.common.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class GanttTaskResponse {

    private UUID id;
    private UUID actionPlanId;
    private UUID departmentKpiId;
    private String departmentKpiName;
    private UUID departmentId;
    private String departmentName;
    private UUID assigneeId;
    private String assigneeName;
    private String name;
    private LocalDate startDate;
    private LocalDate dueDate;
    private TaskStatus status;
    private BigDecimal progressPercent;
    private TaskPriority priority;
    private boolean overdue;
}
