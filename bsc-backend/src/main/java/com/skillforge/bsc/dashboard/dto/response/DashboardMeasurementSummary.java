package com.skillforge.bsc.dashboard.dto.response;

import com.skillforge.bsc.common.enums.KpiDirection;
import com.skillforge.bsc.common.enums.MeasurementStatus;
import com.skillforge.bsc.common.enums.ReportingFrequency;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class DashboardMeasurementSummary {
    private String unit;
    private BigDecimal baselineValue;
    private BigDecimal targetValue;
    private KpiDirection direction;
    private ReportingFrequency reportingFrequency;
    private String formulaDescription;
    private BigDecimal greenThreshold;
    private BigDecimal yellowThreshold;
    private BigDecimal redThreshold;
    private UUID reportOwnerId;
    private String reportOwnerName;
    private MeasurementStatus status;
}
