package com.skillforge.bsc.bsc.b2.mapper;

import com.skillforge.bsc.bsc.b2.dto.response.AnalysisItemResponse;
import com.skillforge.bsc.bsc.b2.dto.response.CandidateStrategyResponse;
import com.skillforge.bsc.bsc.b2.dto.response.CandidateStrategySwotItemResponse;
import com.skillforge.bsc.bsc.b2.dto.response.StrategyBuildingResponse;
import com.skillforge.bsc.bsc.b2.dto.response.SwotItemResponse;
import com.skillforge.bsc.bsc.b2.entity.AnalysisItem;
import com.skillforge.bsc.bsc.b2.entity.CandidateStrategy;
import com.skillforge.bsc.bsc.b2.entity.StrategySwotItem;
import com.skillforge.bsc.bsc.b2.entity.SwotItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class StrategyBuildingMapper {

    public StrategyBuildingResponse toResponse(
            UUID bscStrategyId,
            List<AnalysisItem> analysisItems,
            List<SwotItem> swotItems,
            List<CandidateStrategy> candidateStrategies,
            Map<UUID, List<StrategySwotItem>> mappingsByCandidateId
    ) {
        return StrategyBuildingResponse.builder()
                .bscStrategyId(bscStrategyId)
                .analysisItems(analysisItems.stream().map(this::toResponse).toList())
                .swotItems(swotItems.stream().map(this::toResponse).toList())
                .candidateStrategies(candidateStrategies.stream()
                        .map(strategy -> toResponse(strategy, mappingsByCandidateId.getOrDefault(strategy.getId(), List.of())))
                        .toList())
                .build();
    }

    public AnalysisItemResponse toResponse(AnalysisItem item) {
        return AnalysisItemResponse.builder()
                .id(item.getId())
                .modelType(item.getModelType())
                .factorCode(item.getFactorCode())
                .content(item.getContent())
                .displayOrder(item.getDisplayOrder())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public SwotItemResponse toResponse(SwotItem item) {
        AnalysisItem source = item.getSourceAnalysisItem();
        return SwotItemResponse.builder()
                .id(item.getId())
                .swotType(item.getSwotType())
                .sourceAnalysisItemId(source.getId())
                .sourceModelType(source.getModelType())
                .sourceFactorCode(source.getFactorCode())
                .contentSnapshot(item.getContentSnapshot())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public CandidateStrategyResponse toResponse(CandidateStrategy strategy, List<StrategySwotItem> mappings) {
        return CandidateStrategyResponse.builder()
                .id(strategy.getId())
                .bscStrategyId(strategy.getBscStrategy().getId())
                .strategyGroup(strategy.getStrategyGroup())
                .name(strategy.getName())
                .description(strategy.getDescription())
                .displayOrder(strategy.getDisplayOrder())
                .status(strategy.getStatus())
                .swotItems(mappings.stream().map(this::toCandidateSwotItemResponse).toList())
                .createdAt(strategy.getCreatedAt())
                .updatedAt(strategy.getUpdatedAt())
                .build();
    }

    private CandidateStrategySwotItemResponse toCandidateSwotItemResponse(StrategySwotItem mapping) {
        SwotItem swotItem = mapping.getSwotItem();
        AnalysisItem source = swotItem.getSourceAnalysisItem();
        return CandidateStrategySwotItemResponse.builder()
                .swotItemId(swotItem.getId())
                .swotType(swotItem.getSwotType())
                .sourceAnalysisItemId(source.getId())
                .sourceModelType(source.getModelType())
                .sourceFactorCode(source.getFactorCode())
                .contentSnapshot(swotItem.getContentSnapshot())
                .build();
    }
}
