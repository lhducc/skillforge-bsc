package com.skillforge.bsc.bsc.b7.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class MeasurementTreeResponse {

    private UUID bscStrategyId;
    private int totalWeightedKpis;
    private int configuredMeasurements;
    private int missingMeasurements;
    private boolean valid;
    private List<MeasurementPerspectiveResponse> perspectives;
}
