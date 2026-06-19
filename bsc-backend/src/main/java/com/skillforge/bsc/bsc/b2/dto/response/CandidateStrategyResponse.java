package com.skillforge.bsc.bsc.b2.dto.response;

import com.skillforge.bsc.common.enums.CandidateStrategyStatus;
import com.skillforge.bsc.common.enums.StrategyGroup;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CandidateStrategyResponse {

    private UUID id;
    private UUID bscStrategyId;
    private StrategyGroup strategyGroup;
    private String name;
    private String description;
    private Integer displayOrder;
    private CandidateStrategyStatus status;
    private List<CandidateStrategySwotItemResponse> swotItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
