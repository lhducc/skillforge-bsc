package com.skillforge.bsc.bsc.b4.dto.response;

import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.FinalObjectiveSourceType;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class FinalStrategicObjectiveResponse {

    private UUID id;
    private UUID bscStrategyId;
    private String name;
    private String description;
    private BscPerspectiveCode perspectiveCode;
    private FinalObjectiveSourceType sourceType;
    private Integer displayOrder;
    private StrategicObjectiveStatus status;
    private List<FinalObjectiveSourceResponse> sources;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
