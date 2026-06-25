package com.skillforge.bsc.bsc.b7.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MeasurementPerspectiveResponse {

    private BscPerspectiveCode perspectiveCode;
    private boolean valid;
    private List<MeasurementObjectiveResponse> objectives;
}
