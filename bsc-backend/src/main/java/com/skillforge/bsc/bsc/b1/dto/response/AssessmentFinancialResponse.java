package com.skillforge.bsc.bsc.b1.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AssessmentFinancialResponse {

    private UUID id;
    private Integer year;
    private BigDecimal revenue;
    private BigDecimal profit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
