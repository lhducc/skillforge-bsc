package com.skillforge.bsc.bsc.b4.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class FinalStrategyMapResponse {

    private UUID bscStrategyId;
    private List<SelectedStrategySummaryResponse> selectedStrategies;
    private List<StrategyMapResponse> individualStrategyMaps;
    private List<FinalStrategicObjectiveResponse> finalObjectives;
    private List<FinalObjectiveLinkResponse> finalObjectiveLinks;
}
