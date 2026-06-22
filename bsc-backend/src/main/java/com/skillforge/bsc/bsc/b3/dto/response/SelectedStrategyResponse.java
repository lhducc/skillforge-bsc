package com.skillforge.bsc.bsc.b3.dto.response;

import com.skillforge.bsc.common.enums.StrategyGroup;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SelectedStrategyResponse {

    private UUID selectedStrategyId;
    private UUID bscStrategyId;
    private UUID candidateStrategyId;
    private Integer priorityOrder;
    private String selectionReason;
    private StrategyGroup candidateStrategyGroup;
    private String candidateStrategyName;
    private String candidateStrategyDescription;
    private UUID selectedBy;
    private LocalDateTime selectedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
