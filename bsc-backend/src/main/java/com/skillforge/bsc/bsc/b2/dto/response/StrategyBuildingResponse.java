package com.skillforge.bsc.bsc.b2.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class StrategyBuildingResponse {

    private UUID bscStrategyId;
    private List<AnalysisItemResponse> analysisItems;
    private List<SwotItemResponse> swotItems;
    private List<CandidateStrategyResponse> candidateStrategies;
}
