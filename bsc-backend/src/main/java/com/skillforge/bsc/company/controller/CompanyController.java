package com.skillforge.bsc.company.controller;

import com.skillforge.bsc.common.response.ApiResponse;
import com.skillforge.bsc.company.dto.request.CreateCompanyRequest;
import com.skillforge.bsc.company.dto.request.UpdateCompanyRequest;
import com.skillforge.bsc.company.dto.response.CompanyResponse;
import com.skillforge.bsc.company.service.CompanyService;
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

import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Tag(name = "Company Setup")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @Operation(summary = "Create company")
    public ApiResponse<CompanyResponse> create(@Valid @RequestBody CreateCompanyRequest request) {
        return ApiResponse.success(companyService.create(request));
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "Get company detail")
    public ApiResponse<CompanyResponse> getById(@PathVariable UUID companyId) {
        return ApiResponse.success(companyService.getById(companyId));
    }

    @PutMapping("/{companyId}")
    @Operation(summary = "Update company")
    public ApiResponse<CompanyResponse> update(
            @PathVariable UUID companyId,
            @Valid @RequestBody UpdateCompanyRequest request
    ) {
        return ApiResponse.success(companyService.update(companyId, request));
    }
}
