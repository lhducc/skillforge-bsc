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
public class ObjectiveDashboardResponse {
    private DashboardStrategySummary strategy;
    private List<ObjectiveDetail> objectives;
    private LocalDate asOfDate;

    @Getter
    @Builder
    public static class ObjectiveDetail {
        private UUID objectiveId;
        private String objectiveName;
        private String description;
        private Integer displayOrder;
        private BscPerspectiveCode perspectiveCode;
        private BigDecimal perspectiveWeightPercent;
        private BigDecimal objectiveWeightPercent;
        private BigDecimal weightedScore;
        private BigDecimal completionRate;
        private KpiStatusColor statusColor;
        private List<DashboardKpiItemResponse> kpis;
        private int actionPlanCount;
        private DashboardTaskSummary taskSummary;
    }
}
