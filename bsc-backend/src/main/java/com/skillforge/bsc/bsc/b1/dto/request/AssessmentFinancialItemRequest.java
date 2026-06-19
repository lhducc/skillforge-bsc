package com.skillforge.bsc.bsc.b1.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssessmentFinancialItemRequest {

    @NotNull
    private Integer year;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal revenue;

    @NotNull
    private BigDecimal profit;
}
