package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.KpiReportStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DashboardReportSummary {
    private UUID reportId;
    private String reportingPeriod;
    private BigDecimal actualValue;
    private KpiReportStatus reviewStatus;
    private UUID reporterId;
    private String reporterName;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private String note;
    private String evidenceUrl;
    private boolean selectedForScoring;
}
