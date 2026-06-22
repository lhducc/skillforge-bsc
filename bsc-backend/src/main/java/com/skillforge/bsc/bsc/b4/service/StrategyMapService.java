package com.skillforge.bsc.bsc.b4.service;

import com.skillforge.bsc.bsc.b3.entity.SelectedStrategy;
import com.skillforge.bsc.bsc.b3.repository.SelectedStrategyRepository;
import com.skillforge.bsc.bsc.b4.dto.request.BuildFinalObjectivesRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateFinalObjectiveLinkRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateObjectiveLinkRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateStrategyMapRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateStrategicObjectiveRequest;
import com.skillforge.bsc.bsc.b4.dto.request.FinalObjectiveBuildItemRequest;
import com.skillforge.bsc.bsc.b4.dto.request.UpdateStrategicObjectiveRequest;
import com.skillforge.bsc.bsc.b4.dto.response.FinalObjectiveLinkResponse;
import com.skillforge.bsc.bsc.b4.dto.response.FinalStrategyMapResponse;
import com.skillforge.bsc.bsc.b4.dto.response.ObjectiveLinkResponse;
import com.skillforge.bsc.bsc.b4.dto.response.StrategicObjectiveResponse;
import com.skillforge.bsc.bsc.b4.dto.response.StrategyMapResponse;
import com.skillforge.bsc.bsc.b4.entity.FinalObjectiveLink;
import com.skillforge.bsc.bsc.b4.entity.FinalObjectiveSource;
import com.skillforge.bsc.bsc.b4.entity.FinalStrategicObjective;
import com.skillforge.bsc.bsc.b4.entity.ObjectiveLink;
import com.skillforge.bsc.bsc.b4.entity.StrategicObjective;
import com.skillforge.bsc.bsc.b4.entity.StrategyMap;
import com.skillforge.bsc.bsc.b4.mapper.StrategyMapMapper;
import com.skillforge.bsc.bsc.b4.repository.FinalObjectiveLinkRepository;
import com.skillforge.bsc.bsc.b4.repository.FinalObjectiveSourceRepository;
import com.skillforge.bsc.bsc.b4.repository.FinalStrategicObjectiveRepository;
import com.skillforge.bsc.bsc.b4.repository.ObjectiveLinkRepository;
import com.skillforge.bsc.bsc.b4.repository.StrategicObjectiveRepository;
import com.skillforge.bsc.bsc.b4.repository.StrategyMapRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscPerspectiveCode;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.enums.FinalObjectiveSourceType;
import com.skillforge.bsc.common.enums.StrategicObjectiveStatus;
import com.skillforge.bsc.common.enums.StrategyMapStatus;
import com.skillforge.bsc.common.enums.StrategyMapType;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrategyMapService {

    private static final int INDIVIDUAL_OBJECTIVE_LIMIT = 12;
    private static final int SINGLE_STRATEGY_FINAL_OBJECTIVE_LIMIT = 12;
    private static final int DOUBLE_STRATEGY_FINAL_OBJECTIVE_LIMIT = 24;
    private static final EnumSet<BscPerspectiveCode> ALL_PERSPECTIVES = EnumSet.allOf(BscPerspectiveCode.class);

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final SelectedStrategyRepository selectedStrategyRepository;
    private final StrategyMapRepository strategyMapRepository;
    private final StrategicObjectiveRepository strategicObjectiveRepository;
    private final ObjectiveLinkRepository objectiveLinkRepository;
    private final FinalStrategicObjectiveRepository finalStrategicObjectiveRepository;
    private final FinalObjectiveSourceRepository finalObjectiveSourceRepository;
    private final FinalObjectiveLinkRepository finalObjectiveLinkRepository;
    private final StrategyMapMapper strategyMapMapper;

    @Transactional
    public StrategyMapResponse createStrategyMap(UUID strategyId, CreateStrategyMapRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);

        StrategyMapType mapType = parseEnum(request.getMapType(), StrategyMapType.class, ErrorCode.B4_STRATEGY_MAP_TYPE_INVALID);
        if (mapType != StrategyMapType.INDIVIDUAL) {
            throw new BusinessException(ErrorCode.B4_STRATEGY_MAP_TYPE_INVALID, "Only INDIVIDUAL strategy maps are supported in B4 MVP");
        }

        SelectedStrategy selectedStrategy = getSelectedStrategyForB4(strategyId, request.getSelectedStrategyId());
        if (strategyMapRepository.existsByBscStrategy_IdAndSelectedStrategy_IdAndMapType(
                strategyId,
                selectedStrategy.getId(),
                StrategyMapType.INDIVIDUAL
        )) {
            throw new BusinessException(ErrorCode.B4_STRATEGY_MAP_DUPLICATED);
        }

        StrategyMap strategyMap = new StrategyMap();
        strategyMap.setBscStrategy(strategy);
        strategyMap.setSelectedStrategy(selectedStrategy);
        strategyMap.setMapType(StrategyMapType.INDIVIDUAL);
        strategyMap.setStatus(StrategyMapStatus.DRAFT);
        StrategyMap savedMap = strategyMapRepository.save(strategyMap);

        return buildStrategyMapResponse(savedMap);
    }

    @Transactional
    public StrategicObjectiveResponse createObjective(UUID strategyMapId, CreateStrategicObjectiveRequest request) {
        StrategyMap strategyMap = getIndividualStrategyMap(strategyMapId);
        UUID strategyId = strategyMap.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);
        validateSelectedStrategyMatchesMap(strategyId, strategyMap, request.getSelectedStrategyId());

        long activeObjectiveCount = strategicObjectiveRepository.countBySelectedStrategy_IdAndStatus(
                strategyMap.getSelectedStrategy().getId(),
                StrategicObjectiveStatus.ACTIVE
        );
        if (activeObjectiveCount >= INDIVIDUAL_OBJECTIVE_LIMIT) {
            throw new BusinessException(ErrorCode.B4_OBJECTIVE_LIMIT_EXCEEDED);
        }

        StrategicObjective objective = new StrategicObjective();
        objective.setStrategyMap(strategyMap);
        objective.setSelectedStrategy(strategyMap.getSelectedStrategy());
        applyObjectiveFields(objective, request.getName(), request.getDescription(), request.getPerspectiveCode(), request.getDisplayOrder());
        objective.setStatus(StrategicObjectiveStatus.ACTIVE);

        return strategyMapMapper.toStrategicObjectiveResponse(strategicObjectiveRepository.save(objective));
    }

    @Transactional
    public StrategicObjectiveResponse updateObjective(UUID objectiveId, UpdateStrategicObjectiveRequest request) {
        StrategicObjective objective = getActiveObjective(objectiveId);
        StrategyMap strategyMap = objective.getStrategyMap();
        UUID strategyId = strategyMap.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);
        validateSelectedStrategyMatchesMap(strategyId, strategyMap, request.getSelectedStrategyId());

        applyObjectiveFields(objective, request.getName(), request.getDescription(), request.getPerspectiveCode(), request.getDisplayOrder());
        return strategyMapMapper.toStrategicObjectiveResponse(strategicObjectiveRepository.save(objective));
    }

    @Transactional
    public void deleteObjective(UUID objectiveId) {
        StrategicObjective objective = getActiveObjective(objectiveId);
        StrategyMap strategyMap = objective.getStrategyMap();
        UUID strategyId = strategyMap.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);

        List<ObjectiveLink> links = objectiveLinkRepository.findBySourceObjective_IdOrTargetObjective_Id(objectiveId, objectiveId);
        objectiveLinkRepository.deleteAll(links);
        objective.setStatus(StrategicObjectiveStatus.DELETED);
        strategicObjectiveRepository.save(objective);
    }

    @Transactional
    public ObjectiveLinkResponse createObjectiveLink(UUID strategyMapId, CreateObjectiveLinkRequest request) {
        StrategyMap strategyMap = getIndividualStrategyMap(strategyMapId);
        UUID strategyId = strategyMap.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);

        StrategicObjective source = getActiveObjective(request.getSourceObjectiveId());
        StrategicObjective target = getActiveObjective(request.getTargetObjectiveId());
        validateObjectiveLink(strategyMapId, source, target);

        if (objectiveLinkRepository.existsByStrategyMap_IdAndSourceObjective_IdAndTargetObjective_Id(
                strategyMapId,
                source.getId(),
                target.getId()
        )) {
            throw new BusinessException(ErrorCode.B4_OBJECTIVE_LINK_DUPLICATED);
        }

        ObjectiveLink objectiveLink = new ObjectiveLink();
        objectiveLink.setStrategyMap(strategyMap);
        objectiveLink.setSourceObjective(source);
        objectiveLink.setTargetObjective(target);
        objectiveLink.setNote(normalize(request.getNote()));
        objectiveLink.setDisplayOrder(request.getDisplayOrder());

        return strategyMapMapper.toObjectiveLinkResponse(objectiveLinkRepository.save(objectiveLink));
    }

    @Transactional
    public void deleteObjectiveLink(UUID objectiveLinkId) {
        ObjectiveLink objectiveLink = objectiveLinkRepository.findById(objectiveLinkId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B4_OBJECTIVE_LINK_NOT_FOUND));
        UUID strategyId = objectiveLink.getStrategyMap().getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);

        objectiveLinkRepository.delete(objectiveLink);
    }

    @Transactional
    public FinalStrategyMapResponse buildFinalObjectives(UUID strategyId, BuildFinalObjectivesRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);
        List<SelectedStrategy> selectedStrategies = validateSelectedStrategies(strategyId);
        validateFinalObjectiveCount(request.getItems().size(), selectedStrategies.size());

        Set<UUID> selectedStrategyIds = selectedStrategies.stream()
                .map(SelectedStrategy::getId)
                .collect(Collectors.toSet());
        Set<UUID> usedSourceObjectiveIds = new HashSet<>();
        List<FinalObjectiveBuildPlan> buildPlans = new ArrayList<>();

        for (FinalObjectiveBuildItemRequest item : request.getItems()) {
            buildPlans.add(validateFinalObjectiveBuildItem(strategyId, selectedStrategyIds, usedSourceObjectiveIds, item));
        }

        finalObjectiveLinkRepository.deleteByBscStrategy_Id(strategyId);
        finalObjectiveLinkRepository.flush();
        finalObjectiveSourceRepository.deleteByFinalObjective_BscStrategy_Id(strategyId);
        finalObjectiveSourceRepository.flush();
        finalStrategicObjectiveRepository.deleteByBscStrategy_Id(strategyId);
        finalStrategicObjectiveRepository.flush();

        List<FinalObjectiveSource> sourcesToSave = new ArrayList<>();
        for (FinalObjectiveBuildPlan plan : buildPlans) {
            FinalStrategicObjective finalObjective = new FinalStrategicObjective();
            finalObjective.setBscStrategy(strategy);
            finalObjective.setName(plan.name());
            finalObjective.setDescription(plan.description());
            finalObjective.setPerspectiveCode(plan.perspectiveCode());
            finalObjective.setSourceType(plan.sourceType());
            finalObjective.setDisplayOrder(plan.displayOrder());
            finalObjective.setStatus(StrategicObjectiveStatus.ACTIVE);
            FinalStrategicObjective savedFinalObjective = finalStrategicObjectiveRepository.save(finalObjective);

            for (StrategicObjective sourceObjective : plan.sourceObjectives()) {
                FinalObjectiveSource source = new FinalObjectiveSource();
                source.setFinalObjective(savedFinalObjective);
                source.setSourceObjective(sourceObjective);
                sourcesToSave.add(source);
            }
        }
        finalObjectiveSourceRepository.saveAll(sourcesToSave);

        return buildFinalStrategyMapResponse(strategyId);
    }

    @Transactional
    public FinalObjectiveLinkResponse createFinalObjectiveLink(UUID strategyId, CreateFinalObjectiveLinkRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);

        FinalStrategicObjective source = getActiveFinalObjective(request.getSourceFinalObjectiveId());
        FinalStrategicObjective target = getActiveFinalObjective(request.getTargetFinalObjectiveId());
        validateFinalObjectiveLink(strategyId, source, target);

        if (finalObjectiveLinkRepository.existsByBscStrategy_IdAndSourceFinalObjective_IdAndTargetFinalObjective_Id(
                strategyId,
                source.getId(),
                target.getId()
        )) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_DUPLICATED);
        }

        FinalObjectiveLink link = new FinalObjectiveLink();
        link.setBscStrategy(strategy);
        link.setSourceFinalObjective(source);
        link.setTargetFinalObjective(target);
        link.setNote(normalize(request.getNote()));
        link.setDisplayOrder(request.getDisplayOrder());

        return strategyMapMapper.toFinalObjectiveLinkResponse(finalObjectiveLinkRepository.save(link));
    }

    @Transactional
    public void deleteFinalObjectiveLink(UUID finalObjectiveLinkId) {
        FinalObjectiveLink finalObjectiveLink = finalObjectiveLinkRepository.findById(finalObjectiveLinkId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_NOT_FOUND));
        UUID strategyId = finalObjectiveLink.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);
        ensureB4StepEditable(strategyId);

        finalObjectiveLinkRepository.delete(finalObjectiveLink);
    }

    @Transactional(readOnly = true)
    public FinalStrategyMapResponse getFinalStrategyMap(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        return buildFinalStrategyMapResponse(strategyId);
    }

    @Transactional
    public FinalStrategyMapResponse complete(UUID strategyId) {
        getEditableStrategy(strategyId);
        ensureB3Completed(strategyId);

        BscStepStatus b4Status = getStepStatus(strategyId, BscStepCode.B4_STRATEGY_MAP);
        BscStepStatus b5Status = getStepStatus(strategyId, BscStepCode.B5_FISHBONE);
        if (b4Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b4Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        List<SelectedStrategy> selectedStrategies = validateSelectedStrategies(strategyId);
        validateIndividualMaps(strategyId, selectedStrategies);

        List<FinalStrategicObjective> finalObjectives = finalStrategicObjectiveRepository
                .findByBscStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                        strategyId,
                        StrategicObjectiveStatus.ACTIVE
                );
        validateFinalObjectiveCount(finalObjectives.size(), selectedStrategies.size());
        validateFinalPerspectiveCoverage(finalObjectives);
        validateFinalObjectiveSources(strategyId, selectedStrategies, finalObjectives);
        validatePersistedFinalObjectiveLinks(strategyId, finalObjectives);

        b4Status.setStatus(BscStepStatusValue.COMPLETED);
        b4Status.setCompletedAt(LocalDateTime.now());
        b4Status.setCompletedBy(null);
        b4Status.setInvalidatedReason(null);

        if (b5Status.getStatus() == BscStepStatusValue.LOCKED) {
            b5Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b4Status, b5Status));
        return buildFinalStrategyMapResponse(strategyId);
    }

    private StrategyMapResponse buildStrategyMapResponse(StrategyMap strategyMap) {
        List<StrategicObjective> objectives = strategicObjectiveRepository
                .findByStrategyMap_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                        strategyMap.getId(),
                        StrategicObjectiveStatus.ACTIVE
                );
        Set<UUID> activeObjectiveIds = objectives.stream()
                .map(StrategicObjective::getId)
                .collect(Collectors.toSet());
        List<ObjectiveLink> links = objectiveLinkRepository.findByStrategyMap_IdOrderByDisplayOrderAscCreatedAtAsc(strategyMap.getId())
                .stream()
                .filter(link -> isActiveObjectiveLink(link, activeObjectiveIds))
                .toList();
        return strategyMapMapper.toStrategyMapResponse(strategyMap, objectives, links);
    }

    private FinalStrategyMapResponse buildFinalStrategyMapResponse(UUID strategyId) {
        List<SelectedStrategy> selectedStrategies = selectedStrategyRepository.findByBscStrategy_IdOrderByPriorityOrderAsc(strategyId);
        List<StrategyMap> strategyMaps = strategyMapRepository.findByBscStrategy_IdAndMapTypeOrderByCreatedAtAsc(
                strategyId,
                StrategyMapType.INDIVIDUAL
        );
        List<UUID> strategyMapIds = strategyMaps.stream().map(StrategyMap::getId).toList();

        Map<UUID, List<StrategicObjective>> objectivesByMapId = new HashMap<>();
        Map<UUID, Set<UUID>> activeObjectiveIdsByMapId = new HashMap<>();
        if (!strategyMapIds.isEmpty()) {
            List<StrategicObjective> objectives = strategicObjectiveRepository
                    .findByStrategyMap_IdInAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                            strategyMapIds,
                            StrategicObjectiveStatus.ACTIVE
                    );
            objectivesByMapId = objectives.stream().collect(Collectors.groupingBy(objective -> objective.getStrategyMap().getId()));
            activeObjectiveIdsByMapId = objectives.stream()
                    .collect(Collectors.groupingBy(
                            objective -> objective.getStrategyMap().getId(),
                            Collectors.mapping(StrategicObjective::getId, Collectors.toSet())
                    ));
        }

        Map<UUID, List<ObjectiveLink>> linksByMapId = new HashMap<>();
        if (!strategyMapIds.isEmpty()) {
            Map<UUID, Set<UUID>> finalActiveObjectiveIdsByMapId = activeObjectiveIdsByMapId;
            linksByMapId = objectiveLinkRepository.findByStrategyMap_IdInOrderByDisplayOrderAscCreatedAtAsc(strategyMapIds)
                    .stream()
                    .filter(link -> isActiveObjectiveLink(
                            link,
                            finalActiveObjectiveIdsByMapId.getOrDefault(link.getStrategyMap().getId(), Set.of())
                    ))
                    .collect(Collectors.groupingBy(link -> link.getStrategyMap().getId()));
        }

        List<FinalStrategicObjective> finalObjectives = finalStrategicObjectiveRepository
                .findByBscStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                        strategyId,
                        StrategicObjectiveStatus.ACTIVE
                );
        List<UUID> finalObjectiveIds = finalObjectives.stream().map(FinalStrategicObjective::getId).toList();
        Set<UUID> activeFinalObjectiveIds = new HashSet<>(finalObjectiveIds);

        Map<UUID, List<FinalObjectiveSource>> sourcesByFinalObjectiveId = new HashMap<>();
        if (!finalObjectiveIds.isEmpty()) {
            sourcesByFinalObjectiveId = finalObjectiveSourceRepository.findByFinalObjective_IdIn(finalObjectiveIds)
                    .stream()
                    .filter(source -> source.getSourceObjective().getStatus() == StrategicObjectiveStatus.ACTIVE)
                    .collect(Collectors.groupingBy(source -> source.getFinalObjective().getId()));
        }

        List<FinalObjectiveLink> finalObjectiveLinks = finalObjectiveLinkRepository
                .findByBscStrategy_IdOrderByDisplayOrderAscCreatedAtAsc(strategyId)
                .stream()
                .filter(link -> activeFinalObjectiveIds.contains(link.getSourceFinalObjective().getId())
                        && activeFinalObjectiveIds.contains(link.getTargetFinalObjective().getId()))
                .toList();

        Map<UUID, List<StrategicObjective>> finalObjectivesByMapId = objectivesByMapId;
        Map<UUID, List<ObjectiveLink>> finalLinksByMapId = linksByMapId;
        Map<UUID, List<FinalObjectiveSource>> finalSourcesByFinalObjectiveId = sourcesByFinalObjectiveId;

        return FinalStrategyMapResponse.builder()
                .bscStrategyId(strategyId)
                .selectedStrategies(selectedStrategies.stream().map(strategyMapMapper::toSelectedStrategySummary).toList())
                .individualStrategyMaps(strategyMaps.stream()
                        .map(strategyMap -> strategyMapMapper.toStrategyMapResponse(
                                strategyMap,
                                finalObjectivesByMapId.getOrDefault(strategyMap.getId(), List.of()),
                                finalLinksByMapId.getOrDefault(strategyMap.getId(), List.of())
                        ))
                        .toList())
                .finalObjectives(finalObjectives.stream()
                        .map(finalObjective -> strategyMapMapper.toFinalStrategicObjectiveResponse(
                                finalObjective,
                                finalSourcesByFinalObjectiveId.getOrDefault(finalObjective.getId(), List.of())
                        ))
                        .toList())
                .finalObjectiveLinks(finalObjectiveLinks.stream().map(strategyMapMapper::toFinalObjectiveLinkResponse).toList())
                .build();
    }

    private boolean isActiveObjectiveLink(ObjectiveLink link, Set<UUID> activeObjectiveIds) {
        return activeObjectiveIds.contains(link.getSourceObjective().getId())
                && activeObjectiveIds.contains(link.getTargetObjective().getId());
    }

    private FinalObjectiveBuildPlan validateFinalObjectiveBuildItem(
            UUID strategyId,
            Set<UUID> selectedStrategyIds,
            Set<UUID> usedSourceObjectiveIds,
            FinalObjectiveBuildItemRequest item
    ) {
        String name = normalizeRequired(item.getName(), ErrorCode.B4_FINAL_OBJECTIVE_REQUIRED, "Final objective name must not be blank");
        BscPerspectiveCode perspectiveCode = parseEnum(
                item.getPerspectiveCode(),
                BscPerspectiveCode.class,
                ErrorCode.B4_OBJECTIVE_PERSPECTIVE_REQUIRED
        );
        FinalObjectiveSourceType sourceType = parseEnum(
                item.getSourceType(),
                FinalObjectiveSourceType.class,
                ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID
        );

        List<UUID> sourceObjectiveIds = item.getSourceObjectiveIds();
        if (sourceObjectiveIds == null || sourceObjectiveIds.isEmpty()) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID, "sourceObjectiveIds must not be empty");
        }

        for (UUID sourceObjectiveId : sourceObjectiveIds) {
            if (sourceObjectiveId == null) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
            }
            if (!usedSourceObjectiveIds.add(sourceObjectiveId)) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_DUPLICATED);
            }
        }

        List<StrategicObjective> sourceObjectives = loadSourceObjectives(sourceObjectiveIds);
        validateSourceObjectives(strategyId, selectedStrategyIds, sourceType, perspectiveCode, sourceObjectives);

        return new FinalObjectiveBuildPlan(
                name,
                normalize(item.getDescription()),
                perspectiveCode,
                sourceType,
                item.getDisplayOrder(),
                sourceObjectives
        );
    }

    private List<StrategicObjective> loadSourceObjectives(List<UUID> sourceObjectiveIds) {
        List<StrategicObjective> foundObjectives = strategicObjectiveRepository.findAllById(sourceObjectiveIds);
        Map<UUID, StrategicObjective> objectivesById = foundObjectives.stream()
                .collect(Collectors.toMap(StrategicObjective::getId, Function.identity()));
        List<StrategicObjective> sourceObjectives = sourceObjectiveIds.stream()
                .map(objectivesById::get)
                .toList();
        if (sourceObjectives.stream().anyMatch(objective -> objective == null)) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
        }
        return sourceObjectives;
    }

    private void validateSourceObjectives(
            UUID strategyId,
            Set<UUID> selectedStrategyIds,
            FinalObjectiveSourceType sourceType,
            BscPerspectiveCode perspectiveCode,
            List<StrategicObjective> sourceObjectives
    ) {
        if (sourceType == FinalObjectiveSourceType.MERGED && sourceObjectives.size() < 2) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID, "MERGED final objective requires at least 2 sources");
        }

        BscPerspectiveCode firstPerspective = sourceObjectives.get(0).getPerspectiveCode();
        for (StrategicObjective sourceObjective : sourceObjectives) {
            StrategyMap strategyMap = sourceObjective.getStrategyMap();
            if (sourceObjective.getStatus() != StrategicObjectiveStatus.ACTIVE
                    || strategyMap.getMapType() != StrategyMapType.INDIVIDUAL
                    || !strategyMap.getBscStrategy().getId().equals(strategyId)
                    || !selectedStrategyIds.contains(strategyMap.getSelectedStrategy().getId())) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
            }
            if (sourceType == FinalObjectiveSourceType.MERGED && sourceObjective.getPerspectiveCode() != firstPerspective) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID, "MERGED source objectives must share the same perspective");
            }
            if ((sourceType == FinalObjectiveSourceType.ORIGINAL || sourceType == FinalObjectiveSourceType.MERGED)
                    && sourceObjective.getPerspectiveCode() != perspectiveCode) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID, "Final objective perspective must match source perspective");
            }
        }
    }

    private void validateIndividualMaps(UUID strategyId, List<SelectedStrategy> selectedStrategies) {
        for (SelectedStrategy selectedStrategy : selectedStrategies) {
            StrategyMap strategyMap = strategyMapRepository
                    .findByBscStrategy_IdAndSelectedStrategy_IdAndMapType(
                            strategyId,
                            selectedStrategy.getId(),
                            StrategyMapType.INDIVIDUAL
                    )
                    .orElseThrow(() -> new BusinessException(ErrorCode.B4_STRATEGY_MAP_NOT_FOUND));

            List<StrategicObjective> objectives = strategicObjectiveRepository
                    .findBySelectedStrategy_IdAndStatusOrderByPerspectiveCodeAscDisplayOrderAscCreatedAtAsc(
                            selectedStrategy.getId(),
                            StrategicObjectiveStatus.ACTIVE
                    );
            if (objectives.size() > INDIVIDUAL_OBJECTIVE_LIMIT) {
                throw new BusinessException(ErrorCode.B4_OBJECTIVE_LIMIT_EXCEEDED);
            }
            validateObjectivePerspectiveCoverage(objectives);

            if (!strategyMap.getBscStrategy().getId().equals(strategyId)) {
                throw new BusinessException(ErrorCode.B4_STRATEGY_MAP_NOT_FOUND);
            }
        }
    }

    private void validateObjectivePerspectiveCoverage(List<StrategicObjective> objectives) {
        EnumSet<BscPerspectiveCode> perspectives = objectives.stream()
                .map(StrategicObjective::getPerspectiveCode)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(BscPerspectiveCode.class)));
        if (!perspectives.containsAll(ALL_PERSPECTIVES)) {
            throw new BusinessException(ErrorCode.B4_MISSING_PERSPECTIVE);
        }
    }

    private void validateFinalPerspectiveCoverage(List<FinalStrategicObjective> finalObjectives) {
        if (finalObjectives.isEmpty()) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_REQUIRED);
        }
        EnumSet<BscPerspectiveCode> perspectives = finalObjectives.stream()
                .map(FinalStrategicObjective::getPerspectiveCode)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(BscPerspectiveCode.class)));
        if (!perspectives.containsAll(ALL_PERSPECTIVES)) {
            throw new BusinessException(ErrorCode.B4_MISSING_PERSPECTIVE);
        }
    }

    private void validateFinalObjectiveSources(
            UUID strategyId,
            List<SelectedStrategy> selectedStrategies,
            List<FinalStrategicObjective> finalObjectives
    ) {
        List<UUID> finalObjectiveIds = finalObjectives.stream().map(FinalStrategicObjective::getId).toList();
        Map<UUID, FinalStrategicObjective> finalObjectivesById = finalObjectives.stream()
                .collect(Collectors.toMap(FinalStrategicObjective::getId, Function.identity()));
        Set<UUID> selectedStrategyIds = selectedStrategies.stream()
                .map(SelectedStrategy::getId)
                .collect(Collectors.toSet());
        Set<UUID> usedSourceObjectiveIds = new HashSet<>();

        Map<UUID, List<FinalObjectiveSource>> sourcesByFinalObjectiveId = finalObjectiveSourceRepository
                .findByFinalObjective_IdIn(finalObjectiveIds)
                .stream()
                .collect(Collectors.groupingBy(source -> source.getFinalObjective().getId()));

        for (FinalStrategicObjective finalObjective : finalObjectives) {
            List<FinalObjectiveSource> sources = sourcesByFinalObjectiveId.getOrDefault(finalObjective.getId(), List.of());
            if (sources.isEmpty()) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
            }
            if (finalObjective.getSourceType() == FinalObjectiveSourceType.MERGED && sources.size() < 2) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
            }

            BscPerspectiveCode firstPerspective = sources.get(0).getSourceObjective().getPerspectiveCode();
            for (FinalObjectiveSource source : sources) {
                if (!finalObjectivesById.containsKey(source.getFinalObjective().getId())) {
                    throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
                }
                StrategicObjective sourceObjective = source.getSourceObjective();
                StrategyMap sourceMap = sourceObjective.getStrategyMap();
                if (!usedSourceObjectiveIds.add(sourceObjective.getId())) {
                    throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_DUPLICATED);
                }
                if (sourceObjective.getStatus() != StrategicObjectiveStatus.ACTIVE
                        || sourceMap.getMapType() != StrategyMapType.INDIVIDUAL
                        || !sourceMap.getBscStrategy().getId().equals(strategyId)
                        || !selectedStrategyIds.contains(sourceMap.getSelectedStrategy().getId())) {
                    throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
                }
                if (finalObjective.getSourceType() == FinalObjectiveSourceType.MERGED
                        && sourceObjective.getPerspectiveCode() != firstPerspective) {
                    throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
                }
                if ((finalObjective.getSourceType() == FinalObjectiveSourceType.ORIGINAL
                        || finalObjective.getSourceType() == FinalObjectiveSourceType.MERGED)
                        && sourceObjective.getPerspectiveCode() != finalObjective.getPerspectiveCode()) {
                    throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_SOURCE_INVALID);
                }
            }
        }
    }

    private void validatePersistedFinalObjectiveLinks(UUID strategyId, List<FinalStrategicObjective> finalObjectives) {
        Set<UUID> activeFinalObjectiveIds = finalObjectives.stream()
                .map(FinalStrategicObjective::getId)
                .collect(Collectors.toSet());
        Set<String> seenLinks = new HashSet<>();
        for (FinalObjectiveLink link : finalObjectiveLinkRepository.findByBscStrategy_IdOrderByDisplayOrderAscCreatedAtAsc(strategyId)) {
            UUID sourceId = link.getSourceFinalObjective().getId();
            UUID targetId = link.getTargetFinalObjective().getId();
            if (sourceId.equals(targetId)) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_SELF_REFERENCE);
            }
            if (!activeFinalObjectiveIds.contains(sourceId) || !activeFinalObjectiveIds.contains(targetId)) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_INVALID);
            }
            if (!link.getSourceFinalObjective().getBscStrategy().getId().equals(strategyId)
                    || !link.getTargetFinalObjective().getBscStrategy().getId().equals(strategyId)) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_INVALID);
            }
            if (!seenLinks.add(sourceId + ":" + targetId)) {
                throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_DUPLICATED);
            }
        }
    }

    private void validateFinalObjectiveCount(int finalObjectiveCount, int selectedStrategyCount) {
        if (finalObjectiveCount == 0) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_REQUIRED);
        }

        int limit = switch (selectedStrategyCount) {
            case 1 -> SINGLE_STRATEGY_FINAL_OBJECTIVE_LIMIT;
            case 2 -> DOUBLE_STRATEGY_FINAL_OBJECTIVE_LIMIT;
            default -> throw new BusinessException(ErrorCode.B4_SELECTED_STRATEGY_INVALID);
        };
        if (finalObjectiveCount > limit) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_COUNT_INVALID);
        }
    }

    private void validateObjectiveLink(UUID strategyMapId, StrategicObjective source, StrategicObjective target) {
        if (source.getId().equals(target.getId())) {
            throw new BusinessException(ErrorCode.B4_OBJECTIVE_LINK_SELF_REFERENCE);
        }
        if (!source.getStrategyMap().getId().equals(strategyMapId)
                || !target.getStrategyMap().getId().equals(strategyMapId)) {
            throw new BusinessException(ErrorCode.B4_OBJECTIVE_LINK_INVALID);
        }
    }

    private void validateFinalObjectiveLink(UUID strategyId, FinalStrategicObjective source, FinalStrategicObjective target) {
        if (source.getId().equals(target.getId())) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_SELF_REFERENCE);
        }
        if (!source.getBscStrategy().getId().equals(strategyId)
                || !target.getBscStrategy().getId().equals(strategyId)) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_LINK_INVALID);
        }
    }

    private void applyObjectiveFields(
            StrategicObjective objective,
            String name,
            String description,
            String perspectiveCode,
            Integer displayOrder
    ) {
        objective.setName(normalizeRequired(name, ErrorCode.B4_OBJECTIVE_NAME_REQUIRED, "Objective name must not be blank"));
        objective.setDescription(normalize(description));
        objective.setPerspectiveCode(parseEnum(perspectiveCode, BscPerspectiveCode.class, ErrorCode.B4_OBJECTIVE_PERSPECTIVE_REQUIRED));
        objective.setDisplayOrder(displayOrder);
    }

    private void validateSelectedStrategyMatchesMap(UUID strategyId, StrategyMap strategyMap, UUID selectedStrategyId) {
        SelectedStrategy selectedStrategy = getSelectedStrategyForB4(strategyId, selectedStrategyId);
        if (strategyMap.getSelectedStrategy() == null || !strategyMap.getSelectedStrategy().getId().equals(selectedStrategy.getId())) {
            throw new BusinessException(ErrorCode.B4_SELECTED_STRATEGY_INVALID);
        }
    }

    private List<SelectedStrategy> validateSelectedStrategies(UUID strategyId) {
        List<SelectedStrategy> selectedStrategies = selectedStrategyRepository.findByBscStrategy_IdOrderByPriorityOrderAsc(strategyId);
        if (selectedStrategies.size() < 1 || selectedStrategies.size() > 2) {
            throw new BusinessException(ErrorCode.B4_SELECTED_STRATEGY_INVALID);
        }
        return selectedStrategies;
    }

    private SelectedStrategy getSelectedStrategyForB4(UUID strategyId, UUID selectedStrategyId) {
        SelectedStrategy selectedStrategy = selectedStrategyRepository.findById(selectedStrategyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B4_SELECTED_STRATEGY_INVALID));
        if (!selectedStrategy.getBscStrategy().getId().equals(strategyId)) {
            throw new BusinessException(ErrorCode.B4_SELECTED_STRATEGY_INVALID);
        }
        return selectedStrategy;
    }

    private StrategyMap getIndividualStrategyMap(UUID strategyMapId) {
        StrategyMap strategyMap = strategyMapRepository.findById(strategyMapId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B4_STRATEGY_MAP_NOT_FOUND));
        if (strategyMap.getMapType() != StrategyMapType.INDIVIDUAL || strategyMap.getSelectedStrategy() == null) {
            throw new BusinessException(ErrorCode.B4_STRATEGY_MAP_TYPE_INVALID);
        }
        return strategyMap;
    }

    private StrategicObjective getActiveObjective(UUID objectiveId) {
        StrategicObjective objective = strategicObjectiveRepository.findById(objectiveId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B4_OBJECTIVE_NOT_FOUND));
        if (objective.getStatus() != StrategicObjectiveStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B4_OBJECTIVE_NOT_FOUND);
        }
        return objective;
    }

    private FinalStrategicObjective getActiveFinalObjective(UUID finalObjectiveId) {
        FinalStrategicObjective finalObjective = finalStrategicObjectiveRepository.findById(finalObjectiveId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_NOT_FOUND));
        if (finalObjective.getStatus() != StrategicObjectiveStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B4_FINAL_OBJECTIVE_NOT_FOUND);
        }
        return finalObjective;
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private void ensureB3Completed(UUID strategyId) {
        BscStepStatus b3Status = getStepStatus(strategyId, BscStepCode.B3_STRATEGY_RESULT);
        if (b3Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B3_STRATEGY_RESULT must be completed before B4");
        }
    }

    private void ensureB4StepEditable(UUID strategyId) {
        BscStepStatus b4Status = getStepStatus(strategyId, BscStepCode.B4_STRATEGY_MAP);
        if (b4Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b4Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private <T extends Enum<T>> T parseEnum(String value, Class<T> enumType, ErrorCode errorCode) {
        String normalized = normalizeRequired(value, errorCode, enumType.getSimpleName() + " is required").toUpperCase();
        try {
            return Enum.valueOf(enumType, normalized);
        } catch (IllegalArgumentException ex) {
            throw new BusinessException(errorCode);
        }
    }

    private String normalizeRequired(String value, ErrorCode errorCode, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(errorCode, message);
        }
        return normalized;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private record FinalObjectiveBuildPlan(
            String name,
            String description,
            BscPerspectiveCode perspectiveCode,
            FinalObjectiveSourceType sourceType,
            Integer displayOrder,
            List<StrategicObjective> sourceObjectives
    ) {
    }
}
