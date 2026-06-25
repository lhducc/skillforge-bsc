package com.skillforge.bsc.bsc.b7.dto.request;

import com.skillforge.bsc.common.enums.KpiDirection;
import com.skillforge.bsc.common.enums.ReportingFrequency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpsertKpiMeasurementRequest {

    @NotBlank
    private String unit;

    @DecimalMin("0.000")
    private BigDecimal baselineValue;

    @NotNull
    @DecimalMin("0.000")
    private BigDecimal targetValue;

    @NotNull
    private KpiDirection direction;

    @NotNull
    private ReportingFrequency reportingFrequency;

    private String formulaDescription;

    @DecimalMin("0.000")
    private BigDecimal greenThreshold;

    @DecimalMin("0.000")
    private BigDecimal yellowThreshold;

    @DecimalMin("0.000")
    private BigDecimal redThreshold;

    private UUID reportOwnerId;
}
