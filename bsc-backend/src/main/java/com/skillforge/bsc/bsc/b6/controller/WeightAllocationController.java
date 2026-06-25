package com.skillforge.bsc.bsc.b6.controller;

import com.skillforge.bsc.bsc.b6.dto.request.UpsertKpiWeightsRequest;
import com.skillforge.bsc.bsc.b6.dto.request.UpsertObjectiveWeightsRequest;
import com.skillforge.bsc.bsc.b6.dto.request.UpsertPerspectiveWeightsRequest;
import com.skillforge.bsc.bsc.b6.dto.response.WeightTreeResponse;
import com.skillforge.bsc.bsc.b6.service.WeightAllocationService;
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
@Tag(name = "B6 Weight Allocation")
public class WeightAllocationController {

    private final WeightAllocationService weightAllocationService;

    @PutMapping("/bsc-strategies/{strategyId}/weights/perspectives")
    @Operation(summary = "Upsert B6 perspective weights")
    public ApiResponse<WeightTreeResponse> upsertPerspectiveWeights(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertPerspectiveWeightsRequest request
    ) {
        return ApiResponse.success(weightAllocationService.upsertPerspectiveWeights(strategyId, request));
    }

    @PutMapping("/bsc-strategies/{strategyId}/weights/objectives")
    @Operation(summary = "Upsert B6 objective weights")
    public ApiResponse<WeightTreeResponse> upsertObjectiveWeights(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertObjectiveWeightsRequest request
    ) {
        return ApiResponse.success(weightAllocationService.upsertObjectiveWeights(strategyId, request));
    }

    @PutMapping("/bsc-strategies/{strategyId}/weights/kpis")
    @Operation(summary = "Upsert B6 KPI weights")
    public ApiResponse<WeightTreeResponse> upsertKpiWeights(
            @PathVariable UUID strategyId,
            @Valid @RequestBody UpsertKpiWeightsRequest request
    ) {
        return ApiResponse.success(weightAllocationService.upsertKpiWeights(strategyId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/weights/tree")
    @Operation(summary = "Get B6 weight tree")
    public ApiResponse<WeightTreeResponse> getWeightTree(@PathVariable UUID strategyId) {
        return ApiResponse.success(weightAllocationService.getWeightTree(strategyId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/weights/complete")
    @Operation(summary = "Complete B6 weight allocation")
    public ApiResponse<WeightTreeResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(weightAllocationService.complete(strategyId));
    }
}
