package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CompanyDashboardResponse {
    private DashboardStrategySummary strategy;
    private BigDecimal companyScore;
    private BigDecimal companyCompletionRate;
    private KpiStatusColor statusColor;
    private List<PerspectiveSummary> perspectives;
    private List<ObjectiveSummary> objectives;
    private List<DepartmentSummary> departments;
    private DashboardKpiSummary kpiSummary;
    private DashboardTaskSummary taskSummary;
    private long reportMissingCount;
    private LocalDate asOfDate;

    @Getter
    @Builder
    public static class PerspectiveSummary {
        private BscPerspectiveCode perspectiveCode;
        private BigDecimal weightPercent;
        private BigDecimal weightedScore;
        private BigDecimal completionRate;
        private KpiStatusColor statusColor;
        private int objectiveCount;
        private int kpiCount;
        private long missingReportCount;
    }

    @Getter
    @Builder
    public static class ObjectiveSummary {
        private UUID objectiveId;
        private String objectiveName;
        private BscPerspectiveCode perspectiveCode;
        private BigDecimal objectiveWeightPercent;
        private BigDecimal weightedScore;
        private BigDecimal completionRate;
        private KpiStatusColor statusColor;
        private int departmentCount;
        private DashboardKpiSummary kpiSummary;
        private DashboardTaskSummary taskSummary;
    }

    @Getter
    @Builder
    public static class DepartmentSummary {
        private UUID departmentId;
        private String departmentName;
        private String departmentCode;
        private String departmentColor;
        private BigDecimal allocatedKpiWeightPercent;
        private BigDecimal weightedScore;
        private BigDecimal completionRate;
        private KpiStatusColor statusColor;
        private DashboardKpiSummary kpiSummary;
        private DashboardTaskSummary taskSummary;
    }
}
