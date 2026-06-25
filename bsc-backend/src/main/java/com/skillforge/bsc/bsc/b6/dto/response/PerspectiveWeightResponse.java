package com.skillforge.bsc.bsc.b6.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class PerspectiveWeightResponse {

    private BscPerspectiveCode perspectiveCode;
    private BigDecimal weightPercent;
    private BigDecimal allocatedObjectiveWeightPercent;
    private BigDecimal remainingObjectiveWeightPercent;
    private boolean valid;
    private List<ObjectiveWeightResponse> objectives;
}
