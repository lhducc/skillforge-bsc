package com.skillforge.bsc.bsc.b8.dto.request;

import com.skillforge.bsc.common.enums.KpiReportStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateKpiReportRequest {

    @NotNull
    private UUID departmentKpiId;

    @NotBlank
    private String reportingPeriod;

    @NotNull
    @DecimalMin("0.000")
    private BigDecimal actualValue;

    private String note;

    private String evidenceUrl;

    private KpiReportStatus reviewStatus;

    private UUID reporterId;
}
