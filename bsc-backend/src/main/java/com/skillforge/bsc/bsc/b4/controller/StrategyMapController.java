package com.skillforge.bsc.bsc.b4.controller;

import com.skillforge.bsc.bsc.b4.dto.request.BuildFinalObjectivesRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateFinalObjectiveLinkRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateObjectiveLinkRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateStrategyMapRequest;
import com.skillforge.bsc.bsc.b4.dto.request.CreateStrategicObjectiveRequest;
import com.skillforge.bsc.bsc.b4.dto.request.UpdateStrategicObjectiveRequest;
import com.skillforge.bsc.bsc.b4.dto.response.FinalObjectiveLinkResponse;
import com.skillforge.bsc.bsc.b4.dto.response.FinalStrategyMapResponse;
import com.skillforge.bsc.bsc.b4.dto.response.ObjectiveLinkResponse;
import com.skillforge.bsc.bsc.b4.dto.response.StrategicObjectiveResponse;
import com.skillforge.bsc.bsc.b4.dto.response.StrategyMapResponse;
import com.skillforge.bsc.bsc.b4.service.StrategyMapService;
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

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "B4 Strategy Map")
public class StrategyMapController {

    private final StrategyMapService strategyMapService;

    @PostMapping("/bsc-strategies/{strategyId}/strategy-maps")
    @Operation(summary = "Create B4 individual strategy map")
    public ApiResponse<StrategyMapResponse> createStrategyMap(
            @PathVariable UUID strategyId,
            @Valid @RequestBody CreateStrategyMapRequest request
    ) {
        return ApiResponse.success(strategyMapService.createStrategyMap(strategyId, request));
    }

    @PostMapping("/strategy-maps/{strategyMapId}/objectives")
    @Operation(summary = "Create B4 strategic objective")
    public ApiResponse<StrategicObjectiveResponse> createObjective(
            @PathVariable UUID strategyMapId,
            @Valid @RequestBody CreateStrategicObjectiveRequest request
    ) {
        return ApiResponse.success(strategyMapService.createObjective(strategyMapId, request));
    }

    @PutMapping("/strategic-objectives/{objectiveId}")
    @Operation(summary = "Update B4 strategic objective")
    public ApiResponse<StrategicObjectiveResponse> updateObjective(
            @PathVariable UUID objectiveId,
            @Valid @RequestBody UpdateStrategicObjectiveRequest request
    ) {
        return ApiResponse.success(strategyMapService.updateObjective(objectiveId, request));
    }

    @DeleteMapping("/strategic-objectives/{objectiveId}")
    @Operation(summary = "Delete B4 strategic objective")
    public ApiResponse<Void> deleteObjective(@PathVariable UUID objectiveId) {
        strategyMapService.deleteObjective(objectiveId);
        return ApiResponse.success(null);
    }

    @PostMapping("/strategy-maps/{strategyMapId}/objective-links")
    @Operation(summary = "Create B4 objective link")
    public ApiResponse<ObjectiveLinkResponse> createObjectiveLink(
            @PathVariable UUID strategyMapId,
            @Valid @RequestBody CreateObjectiveLinkRequest request
    ) {
        return ApiResponse.success(strategyMapService.createObjectiveLink(strategyMapId, request));
    }

    @DeleteMapping("/objective-links/{objectiveLinkId}")
    @Operation(summary = "Delete B4 objective link")
    public ApiResponse<Void> deleteObjectiveLink(@PathVariable UUID objectiveLinkId) {
        strategyMapService.deleteObjectiveLink(objectiveLinkId);
        return ApiResponse.success(null);
    }

    @PostMapping("/bsc-strategies/{strategyId}/final-objectives/build")
    @Operation(summary = "Build B4 final strategic objectives")
    public ApiResponse<FinalStrategyMapResponse> buildFinalObjectives(
            @PathVariable UUID strategyId,
            @Valid @RequestBody BuildFinalObjectivesRequest request
    ) {
        return ApiResponse.success(strategyMapService.buildFinalObjectives(strategyId, request));
    }

    @PostMapping("/bsc-strategies/{strategyId}/final-objective-links")
    @Operation(summary = "Create B4 final objective link")
    public ApiResponse<FinalObjectiveLinkResponse> createFinalObjectiveLink(
            @PathVariable UUID strategyId,
            @Valid @RequestBody CreateFinalObjectiveLinkRequest request
    ) {
        return ApiResponse.success(strategyMapService.createFinalObjectiveLink(strategyId, request));
    }

    @DeleteMapping("/final-objective-links/{finalObjectiveLinkId}")
    @Operation(summary = "Delete B4 final objective link")
    public ApiResponse<Void> deleteFinalObjectiveLink(@PathVariable UUID finalObjectiveLinkId) {
        strategyMapService.deleteFinalObjectiveLink(finalObjectiveLinkId);
        return ApiResponse.success(null);
    }

    @GetMapping("/bsc-strategies/{strategyId}/final-strategy-map")
    @Operation(summary = "Get B4 final strategy map")
    public ApiResponse<FinalStrategyMapResponse> getFinalStrategyMap(@PathVariable UUID strategyId) {
        return ApiResponse.success(strategyMapService.getFinalStrategyMap(strategyId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/strategy-map/complete")
    @Operation(summary = "Complete B4 strategy map")
    public ApiResponse<FinalStrategyMapResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(strategyMapService.complete(strategyId));
    }
}
