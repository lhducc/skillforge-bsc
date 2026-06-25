package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.KpiAchievementStatus;
import com.skillforge.bsc.common.enums.KpiReportStatus;
import com.skillforge.bsc.common.enums.KpiStatusColor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class KpiReportResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID departmentKpiId;
    private String departmentKpiName;
    private UUID finalStrategicObjectiveId;
    private String finalStrategicObjectiveName;
    private BscPerspectiveCode perspectiveCode;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private String reportingPeriod;
    private BigDecimal actualValue;
    private BigDecimal completionRate;
    private KpiStatusColor statusColor;
    private KpiAchievementStatus achievementStatus;
    private String note;
    private String evidenceUrl;
    private UUID reporterId;
    private String reporterName;
    private KpiReportStatus reviewStatus;
    private String reviewNote;
    private UUID reviewedById;
    private String reviewedByName;
    private LocalDateTime reviewedAt;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
