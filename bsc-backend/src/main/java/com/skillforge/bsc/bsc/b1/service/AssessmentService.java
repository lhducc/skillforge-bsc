package com.skillforge.bsc.bsc.b1.service;

import com.skillforge.bsc.bsc.b1.dto.request.AssessmentFinancialItemRequest;
import com.skillforge.bsc.bsc.b1.dto.request.AssessmentMarketShareItemRequest;
import com.skillforge.bsc.bsc.b1.dto.request.AssessmentTextItemRequest;
import com.skillforge.bsc.bsc.b1.dto.request.UpsertAssessmentFinancialsRequest;
import com.skillforge.bsc.bsc.b1.dto.request.UpsertAssessmentMarketSharesRequest;
import com.skillforge.bsc.bsc.b1.dto.request.UpsertAssessmentTextItemsRequest;
import com.skillforge.bsc.bsc.b1.dto.response.AssessmentResponse;
import com.skillforge.bsc.bsc.b1.entity.AssessmentFinancial;
import com.skillforge.bsc.bsc.b1.entity.AssessmentMarketShare;
import com.skillforge.bsc.bsc.b1.entity.AssessmentTextItem;
import com.skillforge.bsc.bsc.b1.mapper.AssessmentMapper;
import com.skillforge.bsc.bsc.b1.repository.AssessmentFinancialRepository;
import com.skillforge.bsc.bsc.b1.repository.AssessmentMarketShareRepository;
import com.skillforge.bsc.bsc.b1.repository.AssessmentTextItemRepository;
import com.skillforge.bsc.bsc.strategy.entity.BscStrategy;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.bsc.workflow.entity.BscStepStatus;
import com.skillforge.bsc.bsc.workflow.repository.BscStepStatusRepository;
import com.skillforge.bsc.common.enums.AssessmentMarketSharePeriodType;
import com.skillforge.bsc.common.enums.BscStepCode;
import com.skillforge.bsc.common.enums.BscStepStatusValue;
import com.skillforge.bsc.common.enums.BscStrategyStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private static final int FINANCIAL_LIMIT = 3;
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private final BscStrategyService bscStrategyService;
    private final BscStepStatusRepository bscStepStatusRepository;
    private final AssessmentFinancialRepository assessmentFinancialRepository;
    private final AssessmentMarketShareRepository assessmentMarketShareRepository;
    private final AssessmentTextItemRepository assessmentTextItemRepository;
    private final AssessmentMapper assessmentMapper;

    @Transactional
    public AssessmentResponse upsertFinancials(UUID strategyId, UpsertAssessmentFinancialsRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        validateFinancialItems(request.getItems(), false);

        assessmentFinancialRepository.deleteByBscStrategy_Id(strategyId);
        assessmentFinancialRepository.flush();
        assessmentFinancialRepository.saveAll(request.getItems()
                .stream()
                .map(item -> toFinancial(strategy, item))
                .toList());

        return buildResponse(strategyId);
    }

    @Transactional
    public AssessmentResponse upsertMarketShares(UUID strategyId, UpsertAssessmentMarketSharesRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        validateMarketShareItems(request.getItems(), false);

        assessmentMarketShareRepository.deleteByBscStrategy_Id(strategyId);
        assessmentMarketShareRepository.flush();
        assessmentMarketShareRepository.saveAll(request.getItems()
                .stream()
                .map(item -> toMarketShare(strategy, item))
                .toList());

        return buildResponse(strategyId);
    }

    @Transactional
    public AssessmentResponse upsertTextItems(UUID strategyId, UpsertAssessmentTextItemsRequest request) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        validateTextItems(request.getItems());

        assessmentTextItemRepository.deleteByBscStrategy_Id(strategyId);
        assessmentTextItemRepository.flush();
        assessmentTextItemRepository.saveAll(request.getItems()
                .stream()
                .map(item -> toTextItem(strategy, item))
                .toList());

        return buildResponse(strategyId);
    }

    @Transactional(readOnly = true)
    public AssessmentResponse getAssessment(UUID strategyId) {
        bscStrategyService.getStrategy(strategyId);
        return buildResponse(strategyId);
    }

    @Transactional
    public AssessmentResponse complete(UUID strategyId) {
        BscStrategy strategy = getEditableStrategy(strategyId);
        BscStepStatus b1Status = getStepStatus(strategyId, BscStepCode.B1_ASSESSMENT);
        BscStepStatus b2Status = getStepStatus(strategyId, BscStepCode.B2_STRATEGY_BUILDING);

        if (b1Status.getStatus() == BscStepStatusValue.LOCKED) {
            throw new BusinessException(ErrorCode.STEP_LOCKED);
        }
        if (b1Status.getStatus() == BscStepStatusValue.COMPLETED) {
            throw new BusinessException(ErrorCode.STEP_ALREADY_COMPLETED);
        }

        validateComplete(strategy.getId());

        b1Status.setStatus(BscStepStatusValue.COMPLETED);
        b1Status.setCompletedAt(LocalDateTime.now());
        b1Status.setCompletedBy(null);
        b1Status.setInvalidatedReason(null);

        if (b2Status.getStatus() == BscStepStatusValue.LOCKED) {
            b2Status.setStatus(BscStepStatusValue.NOT_STARTED);
        }

        bscStepStatusRepository.saveAll(List.of(b1Status, b2Status));
        return buildResponse(strategyId);
    }

    private void validateComplete(UUID strategyId) {
        List<AssessmentFinancial> financials = assessmentFinancialRepository.findByBscStrategy_IdOrderByYearAsc(strategyId);
        List<AssessmentMarketShare> marketShares = assessmentMarketShareRepository
                .findByBscStrategy_IdOrderByPeriodTypeAscDisplayOrderAscCompanyNameAsc(strategyId);
        List<AssessmentTextItem> textItems = assessmentTextItemRepository.findByBscStrategy_IdOrderByCategoryAscDisplayOrderAsc(strategyId);

        validateFinancialEntities(financials);
        validateMarketShareEntities(marketShares);
        validateTextEntities(textItems);
    }

    private BscStrategy getEditableStrategy(UUID strategyId) {
        BscStrategy strategy = bscStrategyService.getStrategy(strategyId);
        if (strategy.getStatus() != BscStrategyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.BSC_STRATEGY_NOT_DRAFT);
        }
        return strategy;
    }

    private BscStepStatus getStepStatus(UUID strategyId, BscStepCode stepCode) {
        return bscStepStatusRepository.findByBscStrategyIdAndStepCode(strategyId, stepCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, stepCode + " status not found"));
    }

    private AssessmentResponse buildResponse(UUID strategyId) {
        return assessmentMapper.toResponse(
                strategyId,
                assessmentFinancialRepository.findByBscStrategy_IdOrderByYearAsc(strategyId),
                assessmentMarketShareRepository.findByBscStrategy_IdOrderByPeriodTypeAscDisplayOrderAscCompanyNameAsc(strategyId),
                assessmentTextItemRepository.findByBscStrategy_IdOrderByCategoryAscDisplayOrderAsc(strategyId)
        );
    }

    private AssessmentFinancial toFinancial(BscStrategy strategy, AssessmentFinancialItemRequest item) {
        AssessmentFinancial financial = new AssessmentFinancial();
        financial.setBscStrategy(strategy);
        financial.setYear(item.getYear());
        financial.setRevenue(item.getRevenue());
        financial.setProfit(item.getProfit());
        return financial;
    }

    private AssessmentMarketShare toMarketShare(BscStrategy strategy, AssessmentMarketShareItemRequest item) {
        AssessmentMarketShare marketShare = new AssessmentMarketShare();
        marketShare.setBscStrategy(strategy);
        marketShare.setPeriodType(item.getPeriodType());
        marketShare.setCompanyName(normalizeRequired(item.getCompanyName(), ErrorCode.VALIDATION_ERROR, "companyName is required"));
        marketShare.setMarketSharePercent(item.getMarketSharePercent());
        marketShare.setOwnCompany(item.getOwnCompany());
        marketShare.setDisplayOrder(item.getDisplayOrder());
        return marketShare;
    }

    private AssessmentTextItem toTextItem(BscStrategy strategy, AssessmentTextItemRequest item) {
        AssessmentTextItem textItem = new AssessmentTextItem();
        textItem.setBscStrategy(strategy);
        textItem.setCategory(item.getCategory());
        textItem.setContent(normalizeRequired(item.getContent(), ErrorCode.B1_TEXT_ITEM_EMPTY, "Text item content must not be blank"));
        textItem.setDisplayOrder(item.getDisplayOrder());
        return textItem;
    }

    private void validateFinancialItems(List<AssessmentFinancialItemRequest> items, boolean requireAtLeastOne) {
        if (items == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "items is required");
        }
        if (requireAtLeastOne && items.isEmpty()) {
            throw new BusinessException(ErrorCode.B1_FINANCIAL_REQUIRED);
        }
        if (requireAtLeastOne && items.size() > FINANCIAL_LIMIT) {
            throw new BusinessException(ErrorCode.B1_FINANCIAL_EXCEED_LIMIT);
        }

        Set<Integer> years = new HashSet<>();
        for (AssessmentFinancialItemRequest item : items) {
            if (item.getYear() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "year is required");
            }
            if (!years.add(item.getYear())) {
                throw new BusinessException(ErrorCode.DUPLICATED_RESOURCE, "Financial year must be unique in a BSC strategy");
            }
            if (item.getRevenue() == null || item.getRevenue().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException(ErrorCode.B1_REVENUE_NEGATIVE);
            }
            if (item.getProfit() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "profit is required");
            }
        }
    }

    private void validateMarketShareItems(List<AssessmentMarketShareItemRequest> items, boolean validateCompleteness) {
        if (items == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "items is required");
        }
        for (AssessmentMarketShareItemRequest item : items) {
            if (item.getPeriodType() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "periodType is required");
            }
            normalizeRequired(item.getCompanyName(), ErrorCode.VALIDATION_ERROR, "companyName is required");
            validateMarketSharePercent(item.getMarketSharePercent());
            if (item.getOwnCompany() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "ownCompany is required");
            }
        }
        if (validateCompleteness) {
            validateMarketSharePeriodTotals(items, AssessmentMarketShareItemRequest::getPeriodType,
                    AssessmentMarketShareItemRequest::getMarketSharePercent, AssessmentMarketShareItemRequest::getOwnCompany);
        }
    }

    private void validateTextItems(List<AssessmentTextItemRequest> items) {
        if (items == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "items is required");
        }
        for (AssessmentTextItemRequest item : items) {
            if (item.getCategory() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR, "category is required");
            }
            normalizeRequired(item.getContent(), ErrorCode.B1_TEXT_ITEM_EMPTY, "Text item content must not be blank");
        }
    }

    private void validateFinancialEntities(List<AssessmentFinancial> financials) {
        if (financials.isEmpty()) {
            throw new BusinessException(ErrorCode.B1_FINANCIAL_REQUIRED);
        }
        if (financials.size() > FINANCIAL_LIMIT) {
            throw new BusinessException(ErrorCode.B1_FINANCIAL_EXCEED_LIMIT);
        }

        Set<Integer> years = new HashSet<>();
        for (AssessmentFinancial financial : financials) {
            if (!years.add(financial.getYear())) {
                throw new BusinessException(ErrorCode.DUPLICATED_RESOURCE, "Financial year must be unique in a BSC strategy");
            }
            if (financial.getRevenue() == null || financial.getRevenue().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException(ErrorCode.B1_REVENUE_NEGATIVE);
            }
        }
    }

    private void validateMarketShareEntities(List<AssessmentMarketShare> marketShares) {
        for (AssessmentMarketShare marketShare : marketShares) {
            normalizeRequired(marketShare.getCompanyName(), ErrorCode.VALIDATION_ERROR, "companyName is required");
            validateMarketSharePercent(marketShare.getMarketSharePercent());
        }

        validateMarketSharePeriodTotals(marketShares, AssessmentMarketShare::getPeriodType,
                AssessmentMarketShare::getMarketSharePercent, AssessmentMarketShare::getOwnCompany);
    }

    private <T> void validateMarketSharePeriodTotals(
            List<T> items,
            Function<T, AssessmentMarketSharePeriodType> periodTypeGetter,
            Function<T, BigDecimal> percentGetter,
            Function<T, Boolean> ownCompanyGetter
    ) {
        Map<AssessmentMarketSharePeriodType, List<T>> itemsByPeriod = items.stream()
                .collect(Collectors.groupingBy(periodTypeGetter, () -> new EnumMap<>(AssessmentMarketSharePeriodType.class), Collectors.toList()));

        for (AssessmentMarketSharePeriodType periodType : AssessmentMarketSharePeriodType.values()) {
            List<T> periodItems = itemsByPeriod.getOrDefault(periodType, List.of());
            if (periodItems.isEmpty()) {
                throw new BusinessException(ErrorCode.B1_MARKET_SHARE_TOTAL_INVALID, periodType + " market share data is required");
            }

            BigDecimal total = periodItems.stream()
                    .map(percentGetter)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (total.compareTo(ONE_HUNDRED) != 0) {
                throw new BusinessException(ErrorCode.B1_MARKET_SHARE_TOTAL_INVALID);
            }

            boolean hasOwnCompany = periodItems.stream().anyMatch(item -> Boolean.TRUE.equals(ownCompanyGetter.apply(item)));
            if (!hasOwnCompany) {
                throw new BusinessException(ErrorCode.B1_MARKET_SHARE_OWN_COMPANY_REQUIRED);
            }
        }
    }

    private void validateTextEntities(List<AssessmentTextItem> textItems) {
        for (AssessmentTextItem textItem : textItems) {
            normalizeRequired(textItem.getContent(), ErrorCode.B1_TEXT_ITEM_EMPTY, "Text item content must not be blank");
        }
    }

    private void validateMarketSharePercent(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(ONE_HUNDRED) > 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "marketSharePercent must be between 0 and 100");
        }
    }

    private String normalizeRequired(String value, ErrorCode errorCode, String message) {
        if (value == null) {
            throw new BusinessException(errorCode, message);
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
        return trimmed;
    }
}
