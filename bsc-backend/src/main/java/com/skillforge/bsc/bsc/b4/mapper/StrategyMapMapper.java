package com.skillforge.bsc.bsc.b4.mapper;

import com.skillforge.bsc.bsc.b3.entity.SelectedStrategy;
import com.skillforge.bsc.bsc.b4.dto.response.FinalObjectiveLinkResponse;
import com.skillforge.bsc.bsc.b4.dto.response.FinalObjectiveSourceResponse;
import com.skillforge.bsc.bsc.b4.dto.response.FinalStrategicObjectiveResponse;
import com.skillforge.bsc.bsc.b4.dto.response.ObjectiveLinkResponse;
import com.skillforge.bsc.bsc.b4.dto.response.SelectedStrategySummaryResponse;
import com.skillforge.bsc.bsc.b4.dto.response.StrategicObjectiveResponse;
import com.skillforge.bsc.bsc.b4.dto.response.StrategyMapResponse;
import com.skillforge.bsc.bsc.b4.entity.FinalObjectiveLink;
import com.skillforge.bsc.bsc.b4.entity.FinalObjectiveSource;
import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b4.entity.ObjectiveLink;
import com.skillforge.bsc.bsc.b4.entity.StrategicObjective;
import com.skillforge.bsc.bsc.b4.entity.StrategyMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StrategyMapMapper {

    public SelectedStrategySummaryResponse toSelectedStrategySummary(SelectedStrategy selectedStrategy) {
        return SelectedStrategySummaryResponse.builder()
                .selectedStrategyId(selectedStrategy.getId())
                .candidateStrategyId(selectedStrategy.getCandidateStrategy().getId())
                .priorityOrder(selectedStrategy.getPriorityOrder())
                .selectionReason(selectedStrategy.getSelectionReason())
                .candidateStrategyGroup(selectedStrategy.getCandidateStrategy().getStrategyGroup())
                .candidateStrategyName(selectedStrategy.getCandidateStrategy().getName())
                .candidateStrategyDescription(selectedStrategy.getCandidateStrategy().getDescription())
                .build();
    }

    public StrategyMapResponse toStrategyMapResponse(
            StrategyMap strategyMap,
            List<StrategicObjective> objectives,
            List<ObjectiveLink> objectiveLinks
    ) {
        SelectedStrategy selectedStrategy = strategyMap.getSelectedStrategy();
        return StrategyMapResponse.builder()
                .id(strategyMap.getId())
                .bscStrategyId(strategyMap.getBscStrategy().getId())
                .selectedStrategyId(selectedStrategy == null ? null : selectedStrategy.getId())
                .candidateStrategyId(selectedStrategy == null ? null : selectedStrategy.getCandidateStrategy().getId())
                .selectedStrategyPriorityOrder(selectedStrategy == null ? null : selectedStrategy.getPriorityOrder())
                .candidateStrategyName(selectedStrategy == null ? null : selectedStrategy.getCandidateStrategy().getName())
                .mapType(strategyMap.getMapType())
                .status(strategyMap.getStatus())
                .objectives(objectives.stream().map(this::toStrategicObjectiveResponse).toList())
                .objectiveLinks(objectiveLinks.stream().map(this::toObjectiveLinkResponse).toList())
                .createdAt(strategyMap.getCreatedAt())
                .updatedAt(strategyMap.getUpdatedAt())
                .build();
    }

    public StrategicObjectiveResponse toStrategicObjectiveResponse(StrategicObjective objective) {
        return StrategicObjectiveResponse.builder()
                .id(objective.getId())
                .strategyMapId(objective.getStrategyMap().getId())
                .selectedStrategyId(objective.getSelectedStrategy().getId())
                .name(objective.getName())
                .description(objective.getDescription())
                .perspectiveCode(objective.getPerspectiveCode())
                .displayOrder(objective.getDisplayOrder())
                .status(objective.getStatus())
                .createdAt(objective.getCreatedAt())
                .updatedAt(objective.getUpdatedAt())
                .build();
    }

    public ObjectiveLinkResponse toObjectiveLinkResponse(ObjectiveLink objectiveLink) {
        return ObjectiveLinkResponse.builder()
                .id(objectiveLink.getId())
                .strategyMapId(objectiveLink.getStrategyMap().getId())
                .sourceObjectiveId(objectiveLink.getSourceObjective().getId())
                .targetObjectiveId(objectiveLink.getTargetObjective().getId())
                .note(objectiveLink.getNote())
                .displayOrder(objectiveLink.getDisplayOrder())
                .createdAt(objectiveLink.getCreatedAt())
                .updatedAt(objectiveLink.getUpdatedAt())
                .build();
    }

    public FinalStrategicObjectiveResponse toFinalStrategicObjectiveResponse(
            FinalStrategicObjective finalObjective,
            List<FinalObjectiveSource> sources
    ) {
        return FinalStrategicObjectiveResponse.builder()
                .id(finalObjective.getId())
                .bscStrategyId(finalObjective.getBscStrategy().getId())
                .name(finalObjective.getName())
                .description(finalObjective.getDescription())
                .perspectiveCode(finalObjective.getPerspectiveCode())
                .sourceType(finalObjective.getSourceType())
                .displayOrder(finalObjective.getDisplayOrder())
                .status(finalObjective.getStatus())
                .sources(sources.stream().map(this::toFinalObjectiveSourceResponse).toList())
                .createdAt(finalObjective.getCreatedAt())
                .updatedAt(finalObjective.getUpdatedAt())
                .build();
    }

    public FinalObjectiveSourceResponse toFinalObjectiveSourceResponse(FinalObjectiveSource source) {
        StrategicObjective sourceObjective = source.getSourceObjective();
        return FinalObjectiveSourceResponse.builder()
                .id(source.getId())
                .finalObjectiveId(source.getFinalObjective().getId())
                .sourceObjectiveId(sourceObjective.getId())
                .sourceStrategyMapId(sourceObjective.getStrategyMap().getId())
                .sourceSelectedStrategyId(sourceObjective.getSelectedStrategy().getId())
                .sourceObjectiveName(sourceObjective.getName())
                .sourcePerspectiveCode(sourceObjective.getPerspectiveCode())
                .build();
    }

    public FinalObjectiveLinkResponse toFinalObjectiveLinkResponse(FinalObjectiveLink finalObjectiveLink) {
        return FinalObjectiveLinkResponse.builder()
                .id(finalObjectiveLink.getId())
                .bscStrategyId(finalObjectiveLink.getBscStrategy().getId())
                .sourceFinalObjectiveId(finalObjectiveLink.getSourceFinalObjective().getId())
                .targetFinalObjectiveId(finalObjectiveLink.getTargetFinalObjective().getId())
                .note(finalObjectiveLink.getNote())
                .displayOrder(finalObjectiveLink.getDisplayOrder())
                .createdAt(finalObjectiveLink.getCreatedAt())
                .updatedAt(finalObjectiveLink.getUpdatedAt())
                .build();
    }
}
