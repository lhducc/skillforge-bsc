package com.skillforge.bsc.dashboard.controller;

import com.skillforge.bsc.common.response.ApiResponse;
import com.skillforge.bsc.dashboard.dto.response.CompanyDashboardResponse;
import com.skillforge.bsc.dashboard.dto.response.DepartmentDashboardResponse;
import com.skillforge.bsc.dashboard.dto.response.KpiDashboardDetailResponse;
import com.skillforge.bsc.dashboard.dto.response.ObjectiveDashboardResponse;
import com.skillforge.bsc.dashboard.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dashboard Basic")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard/bsc-strategies/{strategyId}")
    @Operation(summary = "Get basic company BSC dashboard")
    public ApiResponse<CompanyDashboardResponse> getCompanyDashboard(@PathVariable UUID strategyId) {
        return ApiResponse.success(dashboardService.getCompanyDashboard(strategyId));
    }

    @GetMapping("/dashboard/bsc-strategies/{strategyId}/objectives")
    @Operation(summary = "Get basic objective dashboards")
    public ApiResponse<ObjectiveDashboardResponse> getObjectiveDashboard(@PathVariable UUID strategyId) {
        return ApiResponse.success(dashboardService.getObjectiveDashboard(strategyId));
    }

    @GetMapping("/dashboard/bsc-strategies/{strategyId}/departments/{departmentId}")
    @Operation(summary = "Get basic department dashboard")
    public ApiResponse<DepartmentDashboardResponse> getDepartmentDashboard(
            @PathVariable UUID strategyId,
            @PathVariable UUID departmentId
    ) {
        return ApiResponse.success(dashboardService.getDepartmentDashboard(strategyId, departmentId));
    }

    @GetMapping("/dashboard/department-kpis/{departmentKpiId}")
    @Operation(summary = "Get department KPI dashboard detail")
    public ApiResponse<KpiDashboardDetailResponse> getKpiDashboard(@PathVariable UUID departmentKpiId) {
        return ApiResponse.success(dashboardService.getKpiDashboard(departmentKpiId));
    }
}
