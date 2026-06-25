package com.skillforge.bsc.bsc.b6.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class WeightTreeResponse {

    private UUID bscStrategyId;
    private BigDecimal totalWeightPercent;
    private BigDecimal allocatedPerspectiveWeightPercent;
    private BigDecimal remainingPerspectiveWeightPercent;
    private boolean valid;
    private List<PerspectiveWeightResponse> perspectives;
}
