package com.skillforge.bsc.bsc.b4.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FinalObjectiveSourceResponse {

    private UUID id;
    private UUID finalObjectiveId;
    private UUID sourceObjectiveId;
    private UUID sourceStrategyMapId;
    private UUID sourceSelectedStrategyId;
    private String sourceObjectiveName;
    private BscPerspectiveCode sourcePerspectiveCode;
}
