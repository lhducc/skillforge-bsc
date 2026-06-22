package com.skillforge.bsc.bsc.b4.dto.response;

import com.skillforge.bsc.common.enums.StrategyMapStatus;
import com.skillforge.bsc.common.enums.StrategyMapType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class StrategyMapResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID selectedStrategyId;
    private UUID candidateStrategyId;
    private Integer selectedStrategyPriorityOrder;
    private String candidateStrategyName;
    private StrategyMapType mapType;
    private StrategyMapStatus status;
    private List<StrategicObjectiveResponse> objectives;
    private List<ObjectiveLinkResponse> objectiveLinks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
