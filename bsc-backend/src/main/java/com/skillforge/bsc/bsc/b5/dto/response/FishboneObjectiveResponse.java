package com.skillforge.bsc.bsc.b5.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class FishboneObjectiveResponse {

    private UUID finalStrategicObjectiveId;
    private String name;
    private String description;
    private BscPerspectiveCode perspectiveCode;
    private Integer displayOrder;
    private List<DepartmentParticipationResponse> participations;
    private List<DepartmentKpiResponse> departmentKpis;
}
