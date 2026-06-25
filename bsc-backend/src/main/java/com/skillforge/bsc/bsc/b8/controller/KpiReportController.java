package com.skillforge.bsc.bsc.b8.controller;

import com.skillforge.bsc.bsc.b8.dto.request.CreateKpiReportRequest;
import com.skillforge.bsc.bsc.b8.dto.request.ReviewKpiReportRequest;
import com.skillforge.bsc.bsc.b8.dto.response.KpiReportResponse;
import com.skillforge.bsc.bsc.b8.service.KpiReportService;
import com.skillforge.bsc.common.enums.KpiReportStatus;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "B8 KPI Reports")
public class KpiReportController {

    private final KpiReportService kpiReportService;

    @PostMapping("/kpi-reports")
    @Operation(summary = "Create B8 KPI report")
    public ApiResponse<KpiReportResponse> create(@Valid @RequestBody CreateKpiReportRequest request) {
        return ApiResponse.success(kpiReportService.create(request));
    }

    @GetMapping("/bsc-strategies/{strategyId}/kpi-reports")
    @Operation(summary = "List B8 KPI reports by BSC strategy")
    public ApiResponse<List<KpiReportResponse>> listByStrategy(
            @PathVariable UUID strategyId,
            @RequestParam(required = false) UUID departmentKpiId,
            @RequestParam(required = false) UUID departmentId,
            @RequestParam(required = false) KpiReportStatus reviewStatus,
            @RequestParam(required = false) String reportingPeriod
    ) {
        return ApiResponse.success(kpiReportService.listByStrategy(
                strategyId,
                departmentKpiId,
                departmentId,
                reviewStatus,
                reportingPeriod
        ));
    }

    @GetMapping("/department-kpis/{departmentKpiId}/reports")
    @Operation(summary = "List B8 KPI reports by department KPI")
    public ApiResponse<List<KpiReportResponse>> listByDepartmentKpi(@PathVariable UUID departmentKpiId) {
        return ApiResponse.success(kpiReportService.listByDepartmentKpi(departmentKpiId));
    }

    @PatchMapping("/kpi-reports/{reportId}/review")
    @Operation(summary = "Review B8 KPI report")
    public ApiResponse<KpiReportResponse> review(
            @PathVariable UUID reportId,
            @Valid @RequestBody ReviewKpiReportRequest request
    ) {
        return ApiResponse.success(kpiReportService.review(reportId, request));
    }
}
