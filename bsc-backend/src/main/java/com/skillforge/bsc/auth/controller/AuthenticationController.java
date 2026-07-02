package com.skillforge.bsc.auth.controller;

import com.skillforge.bsc.auth.dto.request.LoginRequest;
import com.skillforge.bsc.auth.dto.response.AuthenticatedUserResponse;
import com.skillforge.bsc.auth.dto.response.LoginResponse;
import com.skillforge.bsc.auth.security.CurrentUser;
import com.skillforge.bsc.auth.service.AuthenticationService;
import com.skillforge.bsc.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "Authenticate with email and password")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authenticationService.login(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user")
    public ApiResponse<AuthenticatedUserResponse> me(@AuthenticationPrincipal CurrentUser currentUser) {
        return ApiResponse.success(currentUser.toResponse());
    }
}
