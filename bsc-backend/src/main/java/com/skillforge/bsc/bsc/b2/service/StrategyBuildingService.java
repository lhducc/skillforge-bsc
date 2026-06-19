package com.skillforge.bsc.bsc.b2.service;

import com.skillforge.bsc.bsc.b2.dto.request.AnalysisItemRequest;
import com.skillforge.bsc.bsc.b2.dto.request.CreateCandidateStrategyRequest;
import com.skillforge.bsc.bsc.b2.dto.request.CreateSwotItemRequest;
import com.skillforge.bsc.bsc.b2.dto.request.UpdateCandidateStrategyRequest;
import com.skillforge.bsc.bsc.b2.dto.request.UpsertAnalysisItemsRequest;
import com.skillforge.bsc.bsc.b2.dto.response.CandidateStrategyResponse;
import com.skillforge.bsc.bsc.b2.dto.response.StrategyBuildingResponse;
import com.skillforge.bsc.bsc.b2.entity.AnalysisItem;
import com.skillforge.bsc.bsc.b2.entity.CandidateStrategy;
import com.skillforge.bsc.bsc.b2.entity.StrategySwotItem;
import com.skillforge.bsc.bsc.b2.entity.SwotItem;
import com.skillforge.bsc.bsc.b2.mapper.StrategyBuildingMapper;
import com.skillforge.bsc.bsc.b2.repository.AnalysisItemRepository;
import com.skillforge.bsc.bsc.b2.repository.CandidateStrategyRepository;
import com.skillforge.bsc.bsc.b2.repository.StrategySwotItemRepository;
import com.skillforge.bsc.bsc.b2.repository.SwotItemRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.AnalysisModelType;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.enums.CandidateStrategyStatus;
import com.skillforge.bsc.common.enums.FiveForcesFactor;
import com.skillforge.bsc.common.enums.PestelFactor;
import com.skillforge.bsc.common.enums.SevenSFactor;
import com.skillforge.bsc.common.enums.StrategyGroup;
import com.skillforge.bsc.common.enums.SwotType;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrategyBuildingService {

    private static final int CANDIDATE_STRATEGY_LIMIT = 12;

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final AnalysisItemRepository analysisItemRepository;
    private final SwotItemRepository swotItemRepository;
    private final CandidateStrategyRepository candidateStrategyRepository;
    private final StrategySwotItemRepository strategySwotItemRepository;
    private final StrategyBuildingMapper strategyBuildingMapper;

    @Transactional
    public StrategyBuildingResponse upsertAnalysisItems(UUID strategyId, UpsertAnalysisItemsRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB2StepEditable(strategyId);

        List<AnalysisItem> existingItems = analysisItemRepository
                .findByBscStrategy_IdOrderByModelTypeAscFactorCodeAscDisplayOrderAsc(strategyId);
        Map<UUID, AnalysisItem> existingById = existingItems.stream()
                .collect(Collectors.toMap(AnalysisItem::getId, item -> item));
        Set<UUID> selectedAnalysisItemIds = selectedAnalysisItemIds(existingItems);
        Set<UUID> retainedIds = new HashSet<>();
        List<AnalysisItem> itemsToSave = new ArrayList<>();

        for (AnalysisItemRequest itemRequest : request.getItems()) {
            AnalysisModelType modelType = parseEnum(
                    itemRequest.getModelType(),
                    AnalysisModelType.class,
                    ErrorCode.B2_ANALYSIS_MODEL_TYPE_INVALID
            );
            String factorCode = validateFactorCode(modelType, itemRequest.getFactorCode());
            String content = normalizeRequired(
                    itemRequest.getContent(),
                    ErrorCode.VALIDATION_ERROR,
                    "Analysis item content must not be blank"
            );

            AnalysisItem item = resolveAnalysisItem(strategy, existingById, retainedIds, itemRequest.getId());
            validateSelectedAnalysisItemIsStable(item, selectedAnalysisItemIds, modelType, factorCode, content);

            item.setModelType(modelType);
            item.setFactorCode(factorCode);
            item.setContent(content);
            item.setDisplayOrder(itemRequest.getDisplayOrder());
            itemsToSave.add(item);
        }

        rejectRemovedSelectedAnalysisItems(existingItems, retainedIds, selectedAnalysisItemIds);

        List<AnalysisItem> itemsToDelete = existingItems.stream()
                .filter(item -> !retainedIds.contains(item.getId()))
                .toList();
        analysisItemRepository.deleteAll(itemsToDelete);
        analysisItemRepository.saveAll(itemsToSave);

        return buildResponse(strategyId);
    }

    @Transactional
    public StrategyBuildingResponse createSwotItem(UUID strategyId, CreateSwotItemRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB2StepEditable(strategyId);
        SwotType swotType = parseEnum(request.getSwotType(), SwotType.class, ErrorCode.B2_SWOT_TYPE_INVALID);

        AnalysisItem source = analysisItemRepository.findById(request.getSourceAnalysisItemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.B2_ANALYSIS_ITEM_NOT_FOUND));
        if (!source.getBscStrategy().getId().equals(strategyId)) {
            throw new BusinessException(ErrorCode.B2_ANALYSIS_ITEM_NOT_FOUND);
        }
        validateSwotSource(source, swotType);

        swotItemRepository.findByBscStrategy_IdAndSourceAnalysisItem_Id(strategyId, source.getId())
                .ifPresent(item -> {
                    throw new BusinessException(ErrorCode.B2_SWOT_ITEM_DUPLICATED_SOURCE);
                });

        SwotItem swotItem = new SwotItem();
        swotItem.setBscStrategy(strategy);
        swotItem.setSwotType(swotType);
        swotItem.setSourceAnalysisItem(source);
        swotItem.setContentSnapshot(source.getContent());
        swotItemRepository.save(swotItem);

        return buildResponse(strategyId);
    }

    @Transactional
    public void deleteSwotItem(UUID swotItemId) {
        SwotItem swotItem = getSwotItem(swotItemId);
        UUID strategyId = swotItem.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB2StepEditable(strategyId);

        strategySwotItemRepository.findBySwotItem_IdAndCandidateStrategy_Status(swotItemId, CandidateStrategyStatus.ACTIVE)
                .ifPresent(mapping -> {
                    throw new BusinessException(ErrorCode.B2_SWOT_ITEM_ALREADY_USED);
                });

        swotItemRepository.delete(swotItem);
    }

    @Transactional
    public CandidateStrategyResponse createCandidateStrategy(UUID strategyId, CreateCandidateStrategyRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB2StepEditable(strategyId);
        long activeCount = candidateStrategyRepository.countByBscStrategy_IdAndStatus(strategyId, CandidateStrategyStatus.ACTIVE);
        if (activeCount >= CANDIDATE_STRATEGY_LIMIT) {
            throw new BusinessException(ErrorCode.B2_CANDIDATE_STRATEGY_LIMIT_EXCEEDED);
        }

        StrategyGroup strategyGroup = parseEnum(request.getStrategyGroup(), StrategyGroup.class, ErrorCode.B2_STRATEGY_GROUP_INVALID);
        String name = normalizeRequired(request.getName(), ErrorCode.B2_STRATEGY_NAME_REQUIRED, "Candidate strategy name must not be blank");
        List<SwotItem> swotItems = validateCandidateSwotItems(strategyId, request.getSwotItemIds(), strategyGroup, null);

        CandidateStrategy candidateStrategy = new CandidateStrategy();
        candidateStrategy.setBscStrategy(strategy);
        candidateStrategy.setStrategyGroup(strategyGroup);
        candidateStrategy.setName(name);
        candidateStrategy.setDescription(normalize(request.getDescription()));
        candidateStrategy.setDisplayOrder(request.getDisplayOrder());
        candidateStrategy.setStatus(CandidateStrategyStatus.ACTIVE);
        CandidateStrategy savedStrategy = candidateStrategyRepository.save(candidateStrategy);

        List<StrategySwotItem> mappings = saveMappings(savedStrategy, swotItems);
        return strategyBuildingMapper.toResponse(savedStrategy, mappings);
    }

    @Transactional
    public CandidateStrategyResponse updateCandidateStrategy(UUID candidateStrategyId, UpdateCandidateStrategyRequest request) {
        CandidateStrategy candidateStrategy = getActiveCandidateStrategy(candidateStrategyId);
        UUID strategyId = candidateStrategy.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB2StepEditable(strategyId);

        StrategyGroup strategyGroup = parseEnum(request.getStrategyGroup(), StrategyGroup.class, ErrorCode.B2_STRATEGY_GROUP_INVALID);
        String name = normalizeRequired(request.getName(), ErrorCode.B2_STRATEGY_NAME_REQUIRED, "Candidate strategy name must not be blank");
        List<SwotItem> swotItems = validateCandidateSwotItems(strategyId, request.getSwotItemIds(), strategyGroup, candidateStrategyId);

        candidateStrategy.setStrategyGroup(strategyGroup);
        candidateStrategy.setName(name);
        candidateStrategy.setDescription(normalize(request.getDescription()));
        candidateStrategy.setDisplayOrder(request.getDisplayOrder());

        strategySwotItemRepository.deleteByCandidateStrategy_Id(candidateStrategyId);
        strategySwotItemRepository.flush();
        List<StrategySwotItem> mappings = saveMappings(candidateStrategy, swotItems);

        return strategyBuildingMapper.toResponse(candidateStrategy, mappings);
    }

    @Transactional
    public void deleteCandidateStrategy(UUID candidateStrategyId) {
        CandidateStrategy candidateStrategy = getActiveCandidateStrategy(candidateStrategyId);
        UUID strategyId = candidateStrategy.getBscStrategy().getId();
        getEditableStrategy(strategyId);
        ensureB2StepEditable(strategyId);

        strategySwotItemRepository.deleteByCandidateStrategy_Id(candidateStrategyId);
        strategySwotItemRepository.flush();
        candidateStrategy.setStatus(CandidateStrategyStatus.DELETED);
        candidateStrategyRepository.save(candidateStrategy);
    }

    @Transactional(readOnly = true)
    public List<CandidateStrategyResponse> listCandidateStrategies(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        List<CandidateStrategy> candidateStrategies = activeCandidateStrategies(strategyId);
        Map<UUID, List<StrategySwotItem>> mappingsByCandidateId = mappingsByCandidateId(candidateStrategies);
        return candidateStrategies.stream()
                .map(strategy -> strategyBuildingMapper.toResponse(
                        strategy,
                        mappingsByCandidateId.getOrDefault(strategy.getId(), List.of())
                ))
                .toList();
    }

    @Transactional
    public StrategyBuildingResponse complete(UUID strategyId) {
        getEditableStrategy(strategyId);
        BscStepStatus b1Status = getStepStatus(strategyId, BscStepCode.B1_ASSESSMENT);
        BscStepStatus b2Status = getStepStatus(strategyId, BscStepCode.B2_STRATEGY_BUILDING);
        BscStepStatus b3Status = getStepStatus(strategyId, BscStepCode.B3_STRATEGY_RESULT);

        if (b1Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B1_ASSESSMENT must be completed before B2");
        }
        if (b2Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b2Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        long activeCount = candidateStrategyRepository.countByBscStrategy_IdAndStatus(strategyId, CandidateStrategyStatus.ACTIVE);
        if (activeCount == 0) {
            throw new BusinessException(ErrorCode.B2_CANDIDATE_STRATEGY_REQUIRED);
        }
        if (activeCount > CANDIDATE_STRATEGY_LIMIT) {
            throw new BusinessException(ErrorCode.B2_CANDIDATE_STRATEGY_LIMIT_EXCEEDED);
        }

        b2Status.setStatus(BscStepStatusValue.COMPLETED);
        b2Status.setCompletedAt(LocalDateTime.now());
        b2Status.setCompletedBy(null);
        b2Status.setInvalidatedReason(null);

        if (b3Status.getStatus() == BscStepStatusValue.LOCKED) {
            b3Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b2Status, b3Status));
        return buildResponse(strategyId);
    }

    private AnalysisItem resolveAnalysisItem(
            BscStrategy strategy,
            Map<UUID, AnalysisItem> existingById,
            Set<UUID> retainedIds,
            UUID itemId
    ) {
        if (itemId == null) {
            AnalysisItem item = new AnalysisItem();
            item.setBscStrategy(strategy);
            return item;
        }
        AnalysisItem item = existingById.get(itemId);
        if (item == null) {
            throw new BusinessException(ErrorCode.B2_ANALYSIS_ITEM_NOT_FOUND);
        }
        if (!retainedIds.add(itemId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_RESOURCE, "Analysis item id must be unique in request");
        }
        return item;
    }

    private void validateSelectedAnalysisItemIsStable(
            AnalysisItem item,
            Set<UUID> selectedAnalysisItemIds,
            AnalysisModelType modelType,
            String factorCode,
            String content
    ) {
        if (item.getId() == null || !selectedAnalysisItemIds.contains(item.getId())) {
            return;
        }
        if (item.getModelType() != modelType
                || !Objects.equals(item.getFactorCode(), factorCode)
                || !Objects.equals(item.getContent(), content)) {
            throw new BusinessException(ErrorCode.B2_ANALYSIS_ITEM_SELECTED);
        }
    }

    private void rejectRemovedSelectedAnalysisItems(
            List<AnalysisItem> existingItems,
            Set<UUID> retainedIds,
            Set<UUID> selectedAnalysisItemIds
    ) {
        boolean removedSelectedItem = existingItems.stream()
                .map(AnalysisItem::getId)
                .anyMatch(id -> selectedAnalysisItemIds.contains(id) && !retainedIds.contains(id));
        if (removedSelectedItem) {
            throw new BusinessException(ErrorCode.B2_ANALYSIS_ITEM_SELECTED);
        }
    }

    private Set<UUID> selectedAnalysisItemIds(List<AnalysisItem> existingItems) {
        List<UUID> existingIds = existingItems.stream()
                .map(AnalysisItem::getId)
                .toList();
        if (existingIds.isEmpty()) {
            return Set.of();
        }
        return swotItemRepository.findBySourceAnalysisItem_IdIn(existingIds)
                .stream()
                .map(item -> item.getSourceAnalysisItem().getId())
                .collect(Collectors.toSet());
    }

    private List<SwotItem> validateCandidateSwotItems(
            UUID strategyId,
            List<UUID> swotItemIds,
            StrategyGroup strategyGroup,
            UUID currentCandidateStrategyId
    ) {
        if (swotItemIds == null || swotItemIds.isEmpty()) {
            throw new BusinessException(ErrorCode.B2_STRATEGY_SWOT_RULE_INVALID);
        }

        Set<UUID> uniqueIds = new HashSet<>(swotItemIds);
        if (uniqueIds.size() != swotItemIds.size()) {
            throw new BusinessException(ErrorCode.B2_STRATEGY_SWOT_RULE_INVALID, "SWOT items must not be duplicated");
        }

        List<SwotItem> swotItems = swotItemRepository.findAllById(swotItemIds);
        if (swotItems.size() != swotItemIds.size()) {
            throw new BusinessException(ErrorCode.B2_SWOT_ITEM_NOT_FOUND);
        }
        for (SwotItem swotItem : swotItems) {
            if (!swotItem.getBscStrategy().getId().equals(strategyId)) {
                throw new BusinessException(ErrorCode.B2_STRATEGY_SWOT_RULE_INVALID, "SWOT items must belong to the same BSC strategy");
            }
        }

        rejectAlreadyUsedSwotItems(swotItemIds, currentCandidateStrategyId);
        validateStrategyGroupComposition(strategyGroup, swotItems);
        return swotItems;
    }

    private void rejectAlreadyUsedSwotItems(List<UUID> swotItemIds, UUID currentCandidateStrategyId) {
        List<StrategySwotItem> activeMappings = strategySwotItemRepository
                .findBySwotItem_IdInAndCandidateStrategy_Status(swotItemIds, CandidateStrategyStatus.ACTIVE);
        boolean usedByAnotherCandidate = activeMappings.stream()
                .anyMatch(mapping -> currentCandidateStrategyId == null
                        || !mapping.getCandidateStrategy().getId().equals(currentCandidateStrategyId));
        if (usedByAnotherCandidate) {
            throw new BusinessException(ErrorCode.B2_SWOT_ITEM_ALREADY_USED);
        }
    }

    private void validateStrategyGroupComposition(StrategyGroup strategyGroup, List<SwotItem> swotItems) {
        Map<SwotType, Long> counts = swotItems.stream()
                .collect(Collectors.groupingBy(SwotItem::getSwotType, () -> new EnumMap<>(SwotType.class), Collectors.counting()));
        long strengths = counts.getOrDefault(SwotType.S, 0L);
        long weaknesses = counts.getOrDefault(SwotType.W, 0L);
        long opportunities = counts.getOrDefault(SwotType.O, 0L);
        long threats = counts.getOrDefault(SwotType.T, 0L);

        boolean valid = switch (strategyGroup) {
            case SO -> strengths >= 1 && opportunities == 1 && weaknesses == 0 && threats == 0;
            case ST -> strengths >= 1 && threats == 1 && weaknesses == 0 && opportunities == 0;
            case WO -> weaknesses >= 1 && opportunities == 1 && strengths == 0 && threats == 0;
            case WT -> weaknesses >= 1 && threats == 1 && strengths == 0 && opportunities == 0;
        };
        if (!valid) {
            throw new BusinessException(ErrorCode.B2_STRATEGY_SWOT_RULE_INVALID);
        }
    }

    private List<StrategySwotItem> saveMappings(CandidateStrategy candidateStrategy, List<SwotItem> swotItems) {
        List<StrategySwotItem> mappings = swotItems.stream()
                .map(swotItem -> {
                    StrategySwotItem mapping = new StrategySwotItem();
                    mapping.setCandidateStrategy(candidateStrategy);
                    mapping.setSwotItem(swotItem);
                    return mapping;
                })
                .toList();
        return strategySwotItemRepository.saveAll(mappings);
    }

    private StrategyBuildingResponse buildResponse(UUID strategyId) {
        List<CandidateStrategy> candidateStrategies = activeCandidateStrategies(strategyId);
        return strategyBuildingMapper.toResponse(
                strategyId,
                analysisItemRepository.findByBscStrategy_IdOrderByModelTypeAscFactorCodeAscDisplayOrderAsc(strategyId),
                swotItemRepository.findByBscStrategy_IdOrderBySwotTypeAscCreatedAtAsc(strategyId),
                candidateStrategies,
                mappingsByCandidateId(candidateStrategies)
        );
    }

    private List<CandidateStrategy> activeCandidateStrategies(UUID strategyId) {
        return candidateStrategyRepository.findByBscStrategy_IdAndStatusOrderByDisplayOrderAscCreatedAtAsc(
                strategyId,
                CandidateStrategyStatus.ACTIVE
        );
    }

    private Map<UUID, List<StrategySwotItem>> mappingsByCandidateId(List<CandidateStrategy> candidateStrategies) {
        if (candidateStrategies.isEmpty()) {
            return new HashMap<>();
        }
        List<UUID> candidateIds = candidateStrategies.stream()
                .map(CandidateStrategy::getId)
                .toList();
        return strategySwotItemRepository.findByCandidateStrategy_IdIn(candidateIds)
                .stream()
                .collect(Collectors.groupingBy(mapping -> mapping.getCandidateStrategy().getId()));
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private void ensureB2StepEditable(UUID strategyId) {
        BscStepStatus b2Status = getStepStatus(strategyId, BscStepCode.B2_STRATEGY_BUILDING);
        if (b2Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b2Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private CandidateStrategy getActiveCandidateStrategy(UUID candidateStrategyId) {
        CandidateStrategy candidateStrategy = candidateStrategyRepository.findById(candidateStrategyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B2_CANDIDATE_STRATEGY_NOT_FOUND));
        if (candidateStrategy.getStatus() != CandidateStrategyStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B2_CANDIDATE_STRATEGY_NOT_FOUND);
        }
        return candidateStrategy;
    }

    private SwotItem getSwotItem(UUID swotItemId) {
        return swotItemRepository.findById(swotItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B2_SWOT_ITEM_NOT_FOUND));
    }

    private void validateSwotSource(AnalysisItem source, SwotType swotType) {
        boolean valid = switch (source.getModelType()) {
            case SEVEN_S -> swotType == SwotType.S || swotType == SwotType.W;
            case FIVE_FORCES, PESTEL -> swotType == SwotType.O || swotType == SwotType.T;
        };
        if (!valid) {
            throw new BusinessException(ErrorCode.B2_STRATEGY_SWOT_RULE_INVALID);
        }
    }

    private String validateFactorCode(AnalysisModelType modelType, String factorCode) {
        String normalizedFactorCode = normalizeRequired(
                factorCode,
                ErrorCode.B2_ANALYSIS_FACTOR_INVALID,
                "factorCode is required"
        ).toUpperCase();

        switch (modelType) {
            case SEVEN_S -> parseEnum(normalizedFactorCode, SevenSFactor.class, ErrorCode.B2_ANALYSIS_FACTOR_INVALID);
            case FIVE_FORCES -> parseEnum(normalizedFactorCode, FiveForcesFactor.class, ErrorCode.B2_ANALYSIS_FACTOR_INVALID);
            case PESTEL -> parseEnum(normalizedFactorCode, PestelFactor.class, ErrorCode.B2_ANALYSIS_FACTOR_INVALID);
        }
        return normalizedFactorCode;
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
}
