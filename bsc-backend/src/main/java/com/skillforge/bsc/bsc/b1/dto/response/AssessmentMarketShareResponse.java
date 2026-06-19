package com.skillforge.bsc.bsc.b1.dto.response;

import com.skillforge.bsc.common.enums.AssessmentMarketSharePeriodType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AssessmentMarketShareResponse {

    private UUID id;
    private AssessmentMarketSharePeriodType periodType;
    private String companyName;
    private BigDecimal marketSharePercent;
    private Boolean ownCompany;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
