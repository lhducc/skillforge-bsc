package com.skillforge.bsc.bsc.b4.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class StrategicObjectiveResponse {

    private UUID id;
    private UUID strategyMapId;
    private UUID selectedStrategyId;
    private String name;
    private String description;
    private BscPerspectiveCode perspectiveCode;
    private Integer displayOrder;
    private StrategicObjectiveStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
