package com.skillforge.bsc.user.controller;

import com.skillforge.bsc.common.response.ApiResponse;
import com.skillforge.bsc.user.dto.request.CreateEmployeeRequest;
import com.skillforge.bsc.user.dto.response.EmployeeResponse;
import com.skillforge.bsc.user.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Employee Setup")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/companies/{companyId}/employees")
    @Operation(summary = "Create employee")
    public ApiResponse<EmployeeResponse> create(
            @PathVariable UUID companyId,
            @Valid @RequestBody CreateEmployeeRequest request
    ) {
        return ApiResponse.success(employeeService.create(companyId, request));
    }

    @GetMapping("/companies/{companyId}/employees")
    @Operation(summary = "List employees by company")
    public ApiResponse<List<EmployeeResponse>> listByCompany(
            @PathVariable UUID companyId,
            @RequestParam(required = false) UUID departmentId
    ) {
        return ApiResponse.success(employeeService.listByCompany(companyId, departmentId));
    }
}
