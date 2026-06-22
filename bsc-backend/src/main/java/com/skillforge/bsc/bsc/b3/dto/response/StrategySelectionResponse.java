package com.skillforge.bsc.bsc.b3.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class StrategySelectionResponse {

    private UUID bscStrategyId;
    private List<SelectedStrategyResponse> selectedStrategies;
}
