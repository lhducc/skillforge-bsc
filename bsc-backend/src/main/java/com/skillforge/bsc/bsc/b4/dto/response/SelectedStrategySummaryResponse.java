package com.skillforge.bsc.bsc.b4.dto.response;

import com.skillforge.bsc.common.enums.StrategyGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SelectedStrategySummaryResponse {

    private UUID selectedStrategyId;
    private UUID candidateStrategyId;
    private Integer priorityOrder;
    private String selectionReason;
    private StrategyGroup candidateStrategyGroup;
    private String candidateStrategyName;
    private String candidateStrategyDescription;
}
