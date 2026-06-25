package com.skillforge.bsc.bsc.b7.controller;

import com.skillforge.bsc.bsc.b7.dto.request.UpsertKpiMeasurementRequest;
import com.skillforge.bsc.bsc.b7.dto.response.MeasurementTreeResponse;
import com.skillforge.bsc.bsc.b7.service.KpiMeasurementService;
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
@Tag(name = "B7 KPI Measurement")
public class KpiMeasurementController {

    private final KpiMeasurementService kpiMeasurementService;

    @PutMapping("/department-kpis/{departmentKpiId}/measurement")
    @Operation(summary = "Upsert B7 KPI measurement")
    public ApiResponse<MeasurementTreeResponse> upsertMeasurement(
            @PathVariable UUID departmentKpiId,
            @Valid @RequestBody UpsertKpiMeasurementRequest request
    ) {
        return ApiResponse.success(kpiMeasurementService.upsertMeasurement(departmentKpiId, request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/measurements")
    @Operation(summary = "Get B7 KPI measurements")
    public ApiResponse<MeasurementTreeResponse> getMeasurements(@PathVariable UUID strategyId) {
        return ApiResponse.success(kpiMeasurementService.getMeasurements(strategyId));
    }

    @PostMapping("/bsc-strategies/{strategyId}/measurements/complete")
    @Operation(summary = "Complete B7 KPI measurement")
    public ApiResponse<MeasurementTreeResponse> complete(@PathVariable UUID strategyId) {
        return ApiResponse.success(kpiMeasurementService.complete(strategyId));
    }
}
