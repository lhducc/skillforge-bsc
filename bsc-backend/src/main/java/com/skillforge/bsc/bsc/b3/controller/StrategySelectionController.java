package com.skillforge.bsc.bsc.b3.controller;

import com.skillforge.bsc.bsc.b3.dto.request.SelectStrategiesRequest;
import com.skillforge.bsc.bsc.b3.dto.response.StrategySelectionResponse;
import com.skillforge.bsc.bsc.b3.service.StrategySelectionService;
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
@Tag(name = "B3 Strategy Selection")
public class StrategySelectionController {

    private final StrategySelectionService strategySelectionService;

    @PutMapping("/bsc-strategies/{strategyId}/selected-strategies")
    @Operation(summary = "Replace B3 selected strategies")
    public ApiResponse<StrategySelectionResponse> selectStrategies(
            @PathVariable UUID strategyId,
            @Valid @RequestBody SelectStrategiesRequest request
    ) {
        return ApiResponse.success(strategySelectionService.selectStrategies(strategyId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/selected-strategies")
    @Operation(summary = "Get B3 selected strategies")
    public ApiResponse<StrategySelectionResponse> getSelectedStrategies(@PathVariable UUID strategyId) {
        return ApiResponse.success(strategySelectionService.getSelectedStrategies(strategyId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/strategy-result/complete")
    @Operation(summary = "Complete B3 strategy selection")
    public ApiResponse<StrategySelectionResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(strategySelectionService.complete(strategyId));
    }
}
