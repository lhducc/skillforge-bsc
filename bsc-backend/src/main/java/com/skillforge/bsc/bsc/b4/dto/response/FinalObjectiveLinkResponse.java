package com.skillforge.bsc.bsc.b4.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class FinalObjectiveLinkResponse {

    private UUID id;
    private UUID bscStrategyId;
    private UUID sourceFinalObjectiveId;
    private UUID targetFinalObjectiveId;
    private String note;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
