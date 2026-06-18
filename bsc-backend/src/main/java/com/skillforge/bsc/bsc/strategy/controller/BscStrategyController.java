package com.skillforge.bsc.bsc.strategy.controller;

import com.skillforge.bsc.bsc.strategy.dto.request.CreateBscStrategyRequest;
import com.skillforge.bsc.bsc.strategy.dto.response.BscStrategyResponse;
import com.skillforge.bsc.bsc.strategy.service.BscStrategyService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "BSC Strategy")
public class BscStrategyController {

    private final BscStrategyService bscStrategyService;

    @PostMapping("/companies/{companyId}/bsc-strategies")
    @Operation(summary = "Create BSC strategy")
    public ApiResponse<BscStrategyResponse> create(
            @PathVariable UUID companyId,
            @Valid @RequestBody CreateBscStrategyRequest request
    ) {
        return ApiResponse.success(bscStrategyService.create(companyId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}")
    @Operation(summary = "Get BSC strategy")
    public ApiResponse<BscStrategyResponse> getById(@PathVariable UUID strategyId) {
        return ApiResponse.success(bscStrategyService.getById(strategyId));
    }
}
