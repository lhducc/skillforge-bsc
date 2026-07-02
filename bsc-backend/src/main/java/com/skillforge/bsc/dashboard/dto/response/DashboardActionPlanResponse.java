package com.skillforge.bsc.dashboard.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardActionPlanResponse {
    private UUID actionPlanId;
    private UUID departmentKpiId;
    private String name;
    private UUID ownerId;
    private String ownerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskPriority priority;
    private ActionPlanStatus status;
    private BigDecimal progressPercent;
    private DashboardTaskSummary taskSummary;
    private List<DashboardTaskDetailResponse> tasks;
}
