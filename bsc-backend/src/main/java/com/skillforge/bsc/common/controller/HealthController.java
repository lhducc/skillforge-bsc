package com.skillforge.bsc.common.controller;

import com.skillforge.bsc.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.success(Map.of(
                "status", "UP",
                "service", "bsc-skillforge-backend",
                "timestamp", LocalDateTime.now()
        ));
    }
}