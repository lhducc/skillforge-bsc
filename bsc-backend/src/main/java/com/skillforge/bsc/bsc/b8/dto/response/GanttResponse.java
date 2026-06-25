package com.skillforge.bsc.bsc.b8.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class GanttResponse {

    private UUID bscStrategyId;
    private UUID departmentId;
    private UUID actionPlanId;
    private UUID departmentKpiId;
    private List<GanttActionPlanResponse> actionPlans;
    private List<GanttDependencyResponse> dependencies;
}
