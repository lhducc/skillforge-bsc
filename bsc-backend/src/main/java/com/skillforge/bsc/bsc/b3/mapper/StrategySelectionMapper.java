package com.skillforge.bsc.bsc.b3.mapper;

import com.skillforge.bsc.bsc.b2.entity.CandidateStrategy;
import com.skillforge.bsc.bsc.b3.dto.response.SelectedStrategyResponse;
import com.skillforge.bsc.bsc.b3.dto.response.StrategySelectionResponse;
import com.skillforge.bsc.bsc.b3.entity.SelectedStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class StrategySelectionMapper {

    public StrategySelectionResponse toResponse(UUID bscStrategyId, List<SelectedStrategy> selectedStrategies) {
        return StrategySelectionResponse.builder()
                .bscStrategyId(bscStrategyId)
                .selectedStrategies(selectedStrategies.stream().map(this::toResponse).toList())
                .build();
    }

    public SelectedStrategyResponse toResponse(SelectedStrategy selectedStrategy) {
        CandidateStrategy candidateStrategy = selectedStrategy.getCandidateStrategy();
        return SelectedStrategyResponse.builder()
                .selectedStrategyId(selectedStrategy.getId())
                .bscStrategyId(selectedStrategy.getBscStrategy().getId())
                .candidateStrategyId(candidateStrategy.getId())
                .priorityOrder(selectedStrategy.getPriorityOrder())
                .selectionReason(selectedStrategy.getSelectionReason())
                .candidateStrategyGroup(candidateStrategy.getStrategyGroup())
                .candidateStrategyName(candidateStrategy.getName())
                .candidateStrategyDescription(candidateStrategy.getDescription())
                .selectedBy(selectedStrategy.getSelectedBy())
                .selectedAt(selectedStrategy.getSelectedAt())
                .createdAt(selectedStrategy.getCreatedAt())
                .updatedAt(selectedStrategy.getUpdatedAt())
                .build();
    }
}
