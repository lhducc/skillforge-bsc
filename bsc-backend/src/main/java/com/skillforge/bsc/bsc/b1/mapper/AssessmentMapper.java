package com.skillforge.bsc.bsc.b1.mapper;

import com.skillforge.bsc.bsc.b1.dto.response.AssessmentFinancialResponse;
import com.skillforge.bsc.bsc.b1.dto.response.AssessmentMarketShareResponse;
import com.skillforge.bsc.bsc.b1.dto.response.AssessmentResponse;
import com.skillforge.bsc.bsc.b1.dto.response.AssessmentTextItemResponse;
import com.skillforge.bsc.bsc.b1.entity.AssessmentFinancial;
import com.skillforge.bsc.bsc.b1.entity.AssessmentMarketShare;
import com.skillforge.bsc.bsc.b1.entity.AssessmentTextItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AssessmentMapper {

    public AssessmentResponse toResponse(
            UUID bscStrategyId,
            List<AssessmentFinancial> financials,
            List<AssessmentMarketShare> marketShares,
            List<AssessmentTextItem> textItems
    ) {
        return AssessmentResponse.builder()
                .bscStrategyId(bscStrategyId)
                .financials(financials.stream().map(this::toResponse).toList())
                .marketShares(marketShares.stream().map(this::toResponse).toList())
                .textItems(textItems.stream().map(this::toResponse).toList())
                .build();
    }

    public AssessmentFinancialResponse toResponse(AssessmentFinancial financial) {
        return AssessmentFinancialResponse.builder()
                .id(financial.getId())
                .year(financial.getYear())
                .revenue(financial.getRevenue())
                .profit(financial.getProfit())
                .createdAt(financial.getCreatedAt())
                .updatedAt(financial.getUpdatedAt())
                .build();
    }

    public AssessmentMarketShareResponse toResponse(AssessmentMarketShare marketShare) {
        return AssessmentMarketShareResponse.builder()
                .id(marketShare.getId())
                .periodType(marketShare.getPeriodType())
                .companyName(marketShare.getCompanyName())
                .marketSharePercent(marketShare.getMarketSharePercent())
                .ownCompany(marketShare.getOwnCompany())
                .displayOrder(marketShare.getDisplayOrder())
                .createdAt(marketShare.getCreatedAt())
                .updatedAt(marketShare.getUpdatedAt())
                .build();
    }

    public AssessmentTextItemResponse toResponse(AssessmentTextItem textItem) {
        return AssessmentTextItemResponse.builder()
                .id(textItem.getId())
                .category(textItem.getCategory())
                .content(textItem.getContent())
                .displayOrder(textItem.getDisplayOrder())
                .createdAt(textItem.getCreatedAt())
                .updatedAt(textItem.getUpdatedAt())
                .build();
    }
}
