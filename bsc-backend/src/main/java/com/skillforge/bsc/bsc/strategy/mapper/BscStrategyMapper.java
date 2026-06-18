package com.skillforge.bsc.bsc.strategy.mapper;

import com.skillforge.bsc.bsc.strategy.dto.response.BscStrategyResponse;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import org.springframework.stereotype.Component;

@Component
public class BscStrategyMapper {

    public BscStrategyResponse toResponse(BscStrategy strategy) {
        return BscStrategyResponse.builder()
                .id(strategy.getId())
                .companyId(strategy.getCompany().getId())
                .name(strategy.getName())
                .description(strategy.getDescription())
                .year(strategy.getYear())
                .status(strategy.getStatus())
                .createdBy(strategy.getCreatedBy())
                .activatedAt(strategy.getActivatedAt())
                .createdAt(strategy.getCreatedAt())
                .updatedAt(strategy.getUpdatedAt())
                .build();
    }
}
