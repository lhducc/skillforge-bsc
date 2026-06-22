package com.skillforge.bsc.bsc.b5.controller;

import com.skillforge.bsc.bsc.b5.dto.request.CreateDepartmentKpiRequest;
import com.skillforge.bsc.bsc.b5.dto.request.CreateDepartmentParticipationRequest;
import com.skillforge.bsc.bsc.b5.dto.request.UpdateDepartmentKpiRequest;
import com.skillforge.bsc.bsc.b5.dto.response.CompanyFishboneResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentFishboneResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentKpiResponse;
import com.skillforge.bsc.bsc.b5.dto.response.DepartmentParticipationResponse;
import com.skillforge.bsc.bsc.b5.service.FishboneService;
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
@Tag(name = "B5 Fishbone / Department KPI")
public class FishboneController {

    private final FishboneService fishboneService;

    @PostMapping("/bsc-strategies/{strategyId}/department-participations")
    @Operation(summary = "Department joins B5 final strategic objective")
    public ApiResponse<DepartmentParticipationResponse> joinFinalObjective(
            @PathVariable UUID strategyId,
            @Valid @RequestBody CreateDepartmentParticipationRequest request
    ) {
        return ApiResponse.success(fishboneService.joinFinalObjective(strategyId, request));
    }

    @DeleteMapping("/department-participations/{participationId}")
    @Operation(summary = "Remove B5 department participation")
    public ApiResponse<Void> removeParticipation(@PathVariable UUID participationId) {
        fishboneService.removeParticipation(participationId);
        return ApiResponse.success(null);
    }

    @PostMapping("/department-kpis")
    @Operation(summary = "Create B5 department KPI")
    public ApiResponse<DepartmentKpiResponse> createDepartmentKpi(
            @Valid @RequestBody CreateDepartmentKpiRequest request
    ) {
        return ApiResponse.success(fishboneService.createDepartmentKpi(request));
    }

    @PutMapping("/department-kpis/{departmentKpiId}")
    @Operation(summary = "Update B5 department KPI")
    public ApiResponse<DepartmentKpiResponse> updateDepartmentKpi(
            @PathVariable UUID departmentKpiId,
            @Valid @RequestBody UpdateDepartmentKpiRequest request
    ) {
        return ApiResponse.success(fishboneService.updateDepartmentKpi(departmentKpiId, request));
    }

    @DeleteMapping("/department-kpis/{departmentKpiId}")
    @Operation(summary = "Delete B5 department KPI")
    public ApiResponse<Void> deleteDepartmentKpi(@PathVariable UUID departmentKpiId) {
        fishboneService.deleteDepartmentKpi(departmentKpiId);
        return ApiResponse.success(null);
    }

    @GetMapping("/bsc-strategies/{strategyId}/fishbone/company")
    @Operation(summary = "Get B5 company fishbone")
    public ApiResponse<CompanyFishboneResponse> getCompanyFishbone(@PathVariable UUID strategyId) {
        return ApiResponse.success(fishboneService.getCompanyFishbone(strategyId));
    }

    @GetMapping("/bsc-strategies/{strategyId}/fishbone/departments/{departmentId}")
    @Operation(summary = "Get B5 department fishbone")
    public ApiResponse<DepartmentFishboneResponse> getDepartmentFishbone(
            @PathVariable UUID strategyId,
            @PathVariable UUID departmentId
    ) {
        return ApiResponse.success(fishboneService.getDepartmentFishbone(strategyId, departmentId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/fishbone/complete")
    @Operation(summary = "Complete B5 fishbone")
    public ApiResponse<CompanyFishboneResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(fishboneService.complete(strategyId));
    }
}
