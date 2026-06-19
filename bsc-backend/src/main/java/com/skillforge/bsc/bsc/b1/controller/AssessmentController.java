package com.skillforge.bsc.bsc.b1.controller;

import com.skillforge.bsc.bsc.b1.dto.request.UpsertAssessmentFinancialsRequest;
import com.skillforge.bsc.bsc.b1.dto.request.UpsertAssessmentMarketSharesRequest;
import com.skillforge.bsc.bsc.b1.dto.request.UpsertAssessmentTextItemsRequest;
import com.skillforge.bsc.bsc.b1.dto.response.AssessmentResponse;
import com.skillforge.bsc.bsc.b1.service.AssessmentService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "B1 Assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PutMapping("/bsc-strategies/{strategyId}/assessment/financials")
    @Operation(summary = "Upsert B1 financials")
    public ApiResponse<AssessmentResponse> upsertFinancials(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertAssessmentFinancialsRequest request
    ) {
        return ApiResponse.success(assessmentService.upsertFinancials(strategyId, request));
    }

    @PutMapping("/bsc-strategies/{strategyId}/assessment/market-shares")
    @Operation(summary = "Upsert B1 market shares")
    public ApiResponse<AssessmentResponse> upsertMarketShares(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertAssessmentMarketSharesRequest request
    ) {
        return ApiResponse.success(assessmentService.upsertMarketShares(strategyId, request));
    }

    @PutMapping("/bsc-strategies/{strategyId}/assessment/text-items")
    @Operation(summary = "Upsert B1 text items")
    public ApiResponse<AssessmentResponse> upsertTextItems(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertAssessmentTextItemsRequest request
    ) {
        return ApiResponse.success(assessmentService.upsertTextItems(strategyId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/assessment")
    @Operation(summary = "Get B1 assessment")
    public ApiResponse<AssessmentResponse> getAssessment(@PathVariable UUID strategyId) {
        return ApiResponse.success(assessmentService.getAssessment(strategyId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/assessment/complete")
    @Operation(summary = "Complete B1 assessment")
    public ApiResponse<AssessmentResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(assessmentService.complete(strategyId));
    }
}
