package com.skillforge.bsc.bsc.b2.controller;

import com.skillforge.bsc.bsc.b2.dto.request.CreateCandidateStrategyRequest;
import com.skillforge.bsc.bsc.b2.dto.request.CreateSwotItemRequest;
import com.skillforge.bsc.bsc.b2.dto.request.UpdateCandidateStrategyRequest;
import com.skillforge.bsc.bsc.b2.dto.request.UpsertAnalysisItemsRequest;
import com.skillforge.bsc.bsc.b2.dto.response.CandidateStrategyResponse;
import com.skillforge.bsc.bsc.b2.dto.response.StrategyBuildingResponse;
import com.skillforge.bsc.bsc.b2.service.StrategyBuildingService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "B2 Strategy Building")
public class StrategyBuildingController {

    private final StrategyBuildingService strategyBuildingService;

    @PutMapping("/bsc-strategies/{strategyId}/analysis-items")
    @Operation(summary = "Upsert B2 analysis items")
    public ApiResponse<StrategyBuildingResponse> upsertAnalysisItems(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertAnalysisItemsRequest request
    ) {
        return ApiResponse.success(strategyBuildingService.upsertAnalysisItems(strategyId, request));
    }

    @PostMapping("/bsc-strategies/{strategyId}/swot-items")
    @Operation(summary = "Create B2 SWOT item")
    public ApiResponse<StrategyBuildingResponse> createSwotItem(
            @PathVariable UUID strategyId,
            @Valid @RequestBody CreateSwotItemRequest request
    ) {
        return ApiResponse.success(strategyBuildingService.createSwotItem(strategyId, request));
    }

    @DeleteMapping("/swot-items/{swotItemId}")
    @Operation(summary = "Delete B2 SWOT item")
    public ApiResponse<Void> deleteSwotItem(@PathVariable UUID swotItemId) {
        strategyBuildingService.deleteSwotItem(swotItemId);
        return ApiResponse.success(null);
    }

    @PostMapping("/bsc-strategies/{strategyId}/candidate-strategies")
    @Operation(summary = "Create B2 candidate strategy")
    public ApiResponse<CandidateStrategyResponse> createCandidateStrategy(
            @PathVariable UUID strategyId,
            @Valid @RequestBody CreateCandidateStrategyRequest request
    ) {
        return ApiResponse.success(strategyBuildingService.createCandidateStrategy(strategyId, request));
    }

    @PutMapping("/candidate-strategies/{candidateStrategyId}")
    @Operation(summary = "Update B2 candidate strategy")
    public ApiResponse<CandidateStrategyResponse> updateCandidateStrategy(
            @PathVariable UUID candidateStrategyId,
            @Valid @RequestBody UpdateCandidateStrategyRequest request
    ) {
        return ApiResponse.success(strategyBuildingService.updateCandidateStrategy(candidateStrategyId, request));
    }

    @DeleteMapping("/candidate-strategies/{candidateStrategyId}")
    @Operation(summary = "Delete B2 candidate strategy")
    public ApiResponse<Void> deleteCandidateStrategy(@PathVariable UUID candidateStrategyId) {
        strategyBuildingService.deleteCandidateStrategy(candidateStrategyId);
        return ApiResponse.success(null);
    }

    @GetMapping("/bsc-strategies/{strategyId}/candidate-strategies")
    @Operation(summary = "List B2 candidate strategies")
    public ApiResponse<List<CandidateStrategyResponse>> listCandidateStrategies(@PathVariable UUID strategyId) {
        return ApiResponse.success(strategyBuildingService.listCandidateStrategies(strategyId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/strategy-building/complete")
    @Operation(summary = "Complete B2 strategy building")
    public ApiResponse<StrategyBuildingResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(strategyBuildingService.complete(strategyId));
    }
}
