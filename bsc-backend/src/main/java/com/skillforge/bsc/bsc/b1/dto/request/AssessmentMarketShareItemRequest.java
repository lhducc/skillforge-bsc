package com.skillforge.bsc.bsc.b1.dto.request;

import com.skillforge.bsc.common.enums.AssessmentMarketSharePeriodType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssessmentMarketShareItemRequest {

    @NotNull
    private AssessmentMarketSharePeriodType periodType;

    @NotBlank
    private String companyName;

    @NotNull
    @DecimalMin("0.000")
    @DecimalMax("100.000")
    private BigDecimal marketSharePercent;

    @NotNull
    private Boolean ownCompany;

    private Integer displayOrder;
}
