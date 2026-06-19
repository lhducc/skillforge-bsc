package com.skillforge.bsc.bsc.b1.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class AssessmentResponse {

    private UUID bscStrategyId;
    private List<AssessmentFinancialResponse> financials;
    private List<AssessmentMarketShareResponse> marketShares;
    private List<AssessmentTextItemResponse> textItems;
}
