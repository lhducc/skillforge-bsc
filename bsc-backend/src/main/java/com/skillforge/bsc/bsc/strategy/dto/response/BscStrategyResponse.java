package com.skillforge.bsc.bsc.strategy.dto.response;

import com.skillforge.bsc.common.enums.BscStrategyStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BscStrategyResponse {

    private UUID id;
    private UUID companyId;
    private String name;
    private String description;
    private Integer year;
    private BscStrategyStatus status;
    private UUID createdBy;
    private LocalDateTime activatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
