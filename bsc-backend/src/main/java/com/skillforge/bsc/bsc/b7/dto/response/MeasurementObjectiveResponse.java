package com.skillforge.bsc.bsc.b7.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class MeasurementObjectiveResponse {

    private UUID finalStrategicObjectiveId;
    private String name;
    private String description;
    private BscPerspectiveCode perspectiveCode;
    private Integer displayOrder;
    private boolean valid;
    private List<KpiMeasurementResponse> departmentKpis;
}
