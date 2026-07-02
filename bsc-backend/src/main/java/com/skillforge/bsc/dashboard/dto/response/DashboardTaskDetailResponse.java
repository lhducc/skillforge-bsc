package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.TaskPriority;
import com.skillforge.bsc.common.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class DashboardTaskDetailResponse {
    private UUID taskId;
    private String name;
    private UUID assigneeId;
    private String assigneeName;
    private LocalDate startDate;
    private LocalDate dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private String blockReason;
    private boolean overdue;
}
