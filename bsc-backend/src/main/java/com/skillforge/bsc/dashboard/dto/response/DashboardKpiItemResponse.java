package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.KpiAchievementStatus;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class DashboardKpiItemResponse {
    private UUID departmentKpiId;
    private String kpiName;
    private UUID finalObjectiveId;
    private String finalObjectiveName;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private BigDecimal weightPercent;
    private BigDecimal targetValue;
    private BigDecimal actualValue;
    private String selectedReportingPeriod;
    private DashboardScoringDataStatus scoringDataStatus;
    private BigDecimal completionRate;
    private BigDecimal scoreCompletion;
    private BigDecimal weightedScore;
    private KpiStatusColor statusColor;
    private KpiAchievementStatus achievementStatus;
    private int actionPlanCount;
    private DashboardTaskSummary taskSummary;
}
