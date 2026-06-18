package com.skillforge.bsc.bsc.workflow.mapper;

import com.skillforge.bsc.bsc.workflow.dto.response.BscStepStatusResponse;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import org.springframework.stereotype.Component;

@Component
public class BscStepStatusMapper {

    public BscStepStatusResponse toResponse(BscStepStatus stepStatus) {
        return BscStepStatusResponse.builder()
                .id(stepStatus.getId())
                .bscStrategyId(stepStatus.getBscStrategy().getId())
                .stepCode(stepStatus.getStepCode())
                .status(stepStatus.getStatus())
                .completedBy(stepStatus.getCompletedBy())
                .completedAt(stepStatus.getCompletedAt())
                .invalidatedReason(stepStatus.getInvalidatedReason())
                .createdAt(stepStatus.getCreatedAt())
                .updatedAt(stepStatus.getUpdatedAt())
                .build();
    }
}
