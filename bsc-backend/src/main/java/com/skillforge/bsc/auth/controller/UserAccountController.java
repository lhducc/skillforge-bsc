package com.skillforge.bsc.auth.controller;

import com.skillforge.bsc.auth.dto.request.CreateUserAccountRequest;
import com.skillforge.bsc.auth.dto.response.UserAccountResponse;
import com.skillforge.bsc.auth.service.UserAccountService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Account Setup")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/employees/{employeeId}/account")
    @Operation(summary = "Create user account")
    public ApiResponse<UserAccountResponse> create(
            @PathVariable UUID employeeId,
            @Valid @RequestBody CreateUserAccountRequest request
    ) {
        return ApiResponse.success(userAccountService.create(employeeId, request));
    }
}
