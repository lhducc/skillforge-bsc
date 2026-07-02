package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.DepartmentStatus;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DepartmentDashboardResponse {
    private DashboardStrategySummary strategy;
    private DepartmentSummary department;
    private BigDecimal allocatedKpiWeightPercent;
    private BigDecimal weightedScore;
    private BigDecimal completionRate;
    private KpiStatusColor statusColor;
    private List<DashboardKpiItemResponse> kpis;
    private List<DashboardActionPlanResponse> actionPlans;
    private DashboardTaskSummary taskSummary;
    private long overdueCount;
    private long blockedCount;
    private LocalDate asOfDate;

    @Getter
    @Builder
    public static class DepartmentSummary {
        private UUID departmentId;
        private UUID companyId;
        private String name;
        private String code;
        private String color;
        private DepartmentStatus status;
    }
}
