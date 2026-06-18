package com.skillforge.bsc.bsc.workflow.dto.response;

import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BscStepStatusResponse {

    private UUID id;
    private UUID bscStrategyId;
    private BscStepCode stepCode;
    private BscStepStatusValue status;
    private UUID completedBy;
    private LocalDateTime completedAt;
    private String invalidatedReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
