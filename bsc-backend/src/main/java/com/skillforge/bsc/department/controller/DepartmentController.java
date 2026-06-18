package com.skillforge.bsc.department.controller;

import com.skillforge.bsc.common.response.ApiResponse;
import com.skillforge.bsc.department.dto.request.CreateDepartmentRequest;
import com.skillforge.bsc.department.dto.request.UpdateDepartmentRequest;
import com.skillforge.bsc.department.dto.response.DepartmentResponse;
import com.skillforge.bsc.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Department Setup")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/companies/{companyId}/departments")
    @Operation(summary = "Create department")
    public ApiResponse<DepartmentResponse> create(
            @PathVariable UUID companyId,
            @Valid @RequestBody CreateDepartmentRequest request
    ) {
        return ApiResponse.success(departmentService.create(companyId, request));
    }

    @GetMapping("/companies/{companyId}/departments")
    @Operation(summary = "List departments by company")
    public ApiResponse<List<DepartmentResponse>> listByCompany(@PathVariable UUID companyId) {
        return ApiResponse.success(departmentService.listByCompany(companyId));
    }

    @PutMapping("/departments/{departmentId}")
    @Operation(summary = "Update department")
    public ApiResponse<DepartmentResponse> update(
            @PathVariable UUID departmentId,
            @Valid @RequestBody UpdateDepartmentRequest request
    ) {
        return ApiResponse.success(departmentService.update(departmentId, request));
    }
}
