package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.KpiAchievementStatus;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class KpiDashboardDetailResponse {
    private DashboardStrategySummary strategy;
    private KpiInfo kpi;
    private ObjectiveInfo finalObjective;
    private DepartmentDashboardResponse.DepartmentSummary department;
    private BigDecimal weightPercent;
    private DashboardMeasurementSummary measurement;
    private DashboardReportSummary selectedReport;
    private DashboardScoringDataStatus scoringDataStatus;
    private boolean missingReport;
    private boolean provisional;
    private BigDecimal completionRate;
    private BigDecimal scoreCompletion;
    private BigDecimal weightedScore;
    private KpiStatusColor statusColor;
    private KpiAchievementStatus achievementStatus;
    private DashboardTaskSummary taskSummary;
    private List<DashboardActionPlanResponse> actionPlans;
    private List<DashboardReportSummary> reportHistory;
    private LocalDate asOfDate;

    @Getter
    @Builder
    public static class KpiInfo {
        private UUID departmentKpiId;
        private String name;
        private String description;
        private Integer displayOrder;
    }

    @Getter
    @Builder
    public static class ObjectiveInfo {
        private UUID finalObjectiveId;
        private String name;
        private String description;
        private BscPerspectiveCode perspectiveCode;
    }
}
