package com.skillforge.bsc.bsc.b3.service;

import com.skillforge.bsc.bsc.b2.entity.CandidateStrategy;
import com.skillforge.bsc.bsc.b2.repository.CandidateStrategyRepository;
import com.skillforge.bsc.bsc.b3.dto.request.SelectStrategiesRequest;
import com.skillforge.bsc.bsc.b3.dto.request.SelectedStrategyRequest;
import com.skillforge.bsc.bsc.b3.dto.response.StrategySelectionResponse;
import com.skillforge.bsc.bsc.b3.entity.SelectedStrategy;
import com.skillforge.bsc.bsc.b3.mapper.StrategySelectionMapper;
import com.skillforge.bsc.bsc.b3.repository.SelectedStrategyRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.enums.CandidateStrategyStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StrategySelectionService {

    private static final int MIN_SELECTED_STRATEGIES = 1;
    private static final int MAX_SELECTED_STRATEGIES = 2;

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final CandidateStrategyRepository candidateStrategyRepository;
    private final SelectedStrategyRepository selectedStrategyRepository;
    private final StrategySelectionMapper strategySelectionMapper;

    @Transactional
    public StrategySelectionResponse selectStrategies(UUID strategyId, SelectStrategiesRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        ensureB2Completed(strategyId);
        ensureB3SelectionEditable(strategyId);
        validateSelectionRequest(request);

        List<CandidateStrategy> candidateStrategies = request.getItems().stream()
                .map(item -> getValidCandidateStrategy(strategyId, item.getCandidateStrategyId()))
                .toList();

        selectedStrategyRepository.deleteByBscStrategy_Id(strategyId);
        selectedStrategyRepository.flush();

        LocalDateTime selectedAt = LocalDateTime.now();
        List<SelectedStrategy> selectedStrategies = request.getItems().stream()
                .map(item -> {
                    CandidateStrategy candidateStrategy = findCandidate(candidateStrategies, item.getCandidateStrategyId());
                    SelectedStrategy selectedStrategy = new SelectedStrategy();
                    selectedStrategy.setBscStrategy(strategy);
                    selectedStrategy.setCandidateStrategy(candidateStrategy);
                    selectedStrategy.setPriorityOrder(item.getPriorityOrder());
                    selectedStrategy.setSelectionReason(normalize(item.getSelectionReason()));
                    selectedStrategy.setSelectedBy(null);
                    selectedStrategy.setSelectedAt(selectedAt);
                    return selectedStrategy;
                })
                .toList();

        selectedStrategyRepository.saveAll(selectedStrategies);
        return getSelectedStrategies(strategyId);
    }

    @Transactional(readOnly = true)
    public StrategySelectionResponse getSelectedStrategies(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        return buildResponse(strategyId);
    }

    @Transactional
    public StrategySelectionResponse complete(UUID strategyId) {
        getEditableStrategy(strategyId);
        ensureB2Completed(strategyId);
        BscStepStatus b3Status = getStepStatus(strategyId, BscStepCode.B3_STRATEGY_RESULT);
        BscStepStatus b4Status = getStepStatus(strategyId, BscStepCode.B4_STRATEGY_MAP);

        if (b3Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b3Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        List<SelectedStrategy> selectedStrategies = selectedStrategyRepository
                .findByBscStrategy_IdOrderByPriorityOrderAsc(strategyId);
        validatePersistedSelections(strategyId, selectedStrategies);

        b3Status.setStatus(BscStepStatusValue.COMPLETED);
        b3Status.setCompletedAt(LocalDateTime.now());
        b3Status.setCompletedBy(null);
        b3Status.setInvalidatedReason(null);

        if (b4Status.getStatus() == BscStepStatusValue.LOCKED) {
            b4Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b3Status, b4Status));
        return buildResponse(strategyId);
    }

    private StrategySelectionResponse buildResponse(UUID strategyId) {
        return strategySelectionMapper.toResponse(
                strategyId,
                selectedStrategyRepository.findByBscStrategy_IdOrderByPriorityOrderAsc(strategyId)
        );
    }

    private void validateSelectionRequest(SelectStrategiesRequest request) {
        List<SelectedStrategyRequest> items = request.getItems();
        if (items == null
                || items.size() < MIN_SELECTED_STRATEGIES
                || items.size() > MAX_SELECTED_STRATEGIES) {
            throw new BusinessException(ErrorCode.B3_SELECTED_STRATEGY_COUNT_INVALID);
        }

        Set<UUID> candidateStrategyIds = new HashSet<>();
        Set<Integer> priorityOrders = new HashSet<>();
        for (SelectedStrategyRequest item : items) {
            if (item.getCandidateStrategyId() == null || item.getPriorityOrder() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR);
            }
            if (!candidateStrategyIds.add(item.getCandidateStrategyId())) {
                throw new BusinessException(ErrorCode.B3_SELECTED_STRATEGY_DUPLICATED);
            }
            if (!isValidPriorityOrder(item.getPriorityOrder())) {
                throw new BusinessException(ErrorCode.B3_PRIORITY_ORDER_INVALID);
            }
            if (!priorityOrders.add(item.getPriorityOrder())) {
                throw new BusinessException(ErrorCode.B3_PRIORITY_ORDER_DUPLICATED);
            }
        }
    }

    private void validatePersistedSelections(UUID strategyId, List<SelectedStrategy> selectedStrategies) {
        if (selectedStrategies.size() < MIN_SELECTED_STRATEGIES
                || selectedStrategies.size() > MAX_SELECTED_STRATEGIES) {
            throw new BusinessException(ErrorCode.B3_SELECTED_STRATEGY_COUNT_INVALID);
        }

        Set<UUID> candidateStrategyIds = new HashSet<>();
        Set<Integer> priorityOrders = new HashSet<>();
        for (SelectedStrategy selectedStrategy : selectedStrategies) {
            UUID candidateStrategyId = selectedStrategy.getCandidateStrategy().getId();
            if (!candidateStrategyIds.add(candidateStrategyId)) {
                throw new BusinessException(ErrorCode.B3_SELECTED_STRATEGY_DUPLICATED);
            }
            if (!isValidPriorityOrder(selectedStrategy.getPriorityOrder())) {
                throw new BusinessException(ErrorCode.B3_PRIORITY_ORDER_INVALID);
            }
            if (!priorityOrders.add(selectedStrategy.getPriorityOrder())) {
                throw new BusinessException(ErrorCode.B3_PRIORITY_ORDER_DUPLICATED);
            }
            getValidCandidateStrategy(strategyId, candidateStrategyId);
        }
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private void ensureB2Completed(UUID strategyId) {
        BscStepStatus b2Status = getStepStatus(strategyId, BscStepCode.B2_STRATEGY_BUILDING);
        if (b2Status.getStatus() != BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_NOT_COMPLETED, "B2_STRATEGY_BUILDING must be completed before B3");
        }
    }

    private void ensureB3SelectionEditable(UUID strategyId) {
        BscStepStatus b3Status = getStepStatus(strategyId, BscStepCode.B3_STRATEGY_RESULT);
        if (b3Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b3Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private CandidateStrategy getValidCandidateStrategy(UUID strategyId, UUID candidateStrategyId) {
        CandidateStrategy candidateStrategy = candidateStrategyRepository.findById(candidateStrategyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.B3_CANDIDATE_STRATEGY_INVALID));
        if (!candidateStrategy.getBscStrategy().getId().equals(strategyId)
                || candidateStrategy.getStatus() != CandidateStrategyStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.B3_CANDIDATE_STRATEGY_INVALID);
        }
        return candidateStrategy;
    }

    private CandidateStrategy findCandidate(List<CandidateStrategy> candidates, UUID candidateStrategyId) {
        return candidates.stream()
                .filter(candidate -> candidate.getId().equals(candidateStrategyId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.B3_CANDIDATE_STRATEGY_INVALID));
    }

    private boolean isValidPriorityOrder(Integer priorityOrder) {
        return priorityOrder != null && priorityOrder >= MIN_SELECTED_STRATEGIES && priorityOrder <= MAX_SELECTED_STRATEGIES;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
