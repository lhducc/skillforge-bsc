package com.skillforge.bsc.bsc.workflow.controller;

import com.skillforge.bsc.bsc.workflow.dto.response.BscStepStatusResponse;
import com.skillforge.bsc.bsc.workflow.service.BscStepStatusService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "BSC Workflow")
public class BscStepStatusController {

    private final BscStepStatusService bscStepStatusService;

    @GetMapping("/bsc-strategies/{strategyId}/steps")
    @Operation(summary = "Get BSC strategy step statuses")
    public ApiResponse<List<BscStepStatusResponse>> listByStrategy(@PathVariable UUID strategyId) {
        return ApiResponse.success(bscStepStatusService.listByStrategy(strategyId));
    }
}
