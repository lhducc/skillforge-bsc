package com.skillforge.bsc.bsc.b8.controller;

import com.skillforge.bsc.bsc.b8.dto.request.CreateActionPlanRequest;
import com.skillforge.bsc.bsc.b8.dto.request.UpdateActionPlanRequest;
import com.skillforge.bsc.bsc.b8.dto.response.ActionPlanResponse;
import com.skillforge.bsc.bsc.b8.dto.response.B8CompletionResponse;
import com.skillforge.bsc.bsc.b8.service.ActionPlanService;
import com.skillforge.bsc.bsc.b8.service.B8CompletionService;
import com.skillforge.bsc.common.enums.ActionPlanStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "B8 Action Plans")
public class ActionPlanController {

    private final ActionPlanService actionPlanService;
    private final B8CompletionService b8CompletionService;

    @PostMapping("/action-plans")
    @Operation(summary = "Create B8 action plan")
    public ApiResponse<ActionPlanResponse> create(@Valid @RequestBody CreateActionPlanRequest request) {
        return ApiResponse.success(actionPlanService.create(request));
    }

    @PutMapping("/action-plans/{actionPlanId}")
    @Operation(summary = "Update B8 action plan")
    public ApiResponse<ActionPlanResponse> update(
            @PathVariable UUID actionPlanId,
            @Valid @RequestBody UpdateActionPlanRequest request
    ) {
        return ApiResponse.success(actionPlanService.update(actionPlanId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/action-plans")
    @Operation(summary = "List B8 action plans")
    public ApiResponse<List<ActionPlanResponse>> list(
            @PathVariable UUID strategyId,
            @RequestParam(required = false) UUID departmentKpiId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) UUID ownerId,
            @RequestParam(required = false) ActionPlanStatus status
    ) {
        return ApiResponse.success(actionPlanService.list(strategyId, departmentKpiId, departmentId, ownerId, status));
    }

    @PostMapping("/bsc-strategies/{strategyId}/action-plan/complete")
    @Operation(summary = "Complete B8 action plan setup")
    public ApiResponse<B8CompletionResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(b8CompletionService.complete(strategyId));
    }
}
