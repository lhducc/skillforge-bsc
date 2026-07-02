package com.skillforge.bsc.dashboard.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardKpiSummary {
    private long total;
    private long approved;
    private long provisional;
    private long missingReport;
    private long missingMeasurement;
    private long green;
    private long yellow;
    private long red;
    private long inProgress;
    private long achieved;
    private long exceeded;
}
