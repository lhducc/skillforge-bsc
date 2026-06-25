package com.skillforge.bsc.bsc.b8.dto.response;

import com.skillforge.bsc.common.enums.BscStepStatusValue;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class B8CompletionResponse {

    private UUID bscStrategyId;
    private BscStepStatusValue stepStatus;
    private long actionPlanCount;
    private long taskCount;
    private LocalDateTime completedAt;
}
