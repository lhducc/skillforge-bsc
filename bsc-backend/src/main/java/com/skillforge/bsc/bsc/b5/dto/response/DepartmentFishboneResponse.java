package com.skillforge.bsc.bsc.b5.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DepartmentFishboneResponse {

    private UUID bscStrategyId;
    private UUID departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentColor;
    private List<FishboneObjectiveResponse> objectives;
}
