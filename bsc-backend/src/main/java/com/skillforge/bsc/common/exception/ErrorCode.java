package com.skillforge.bsc.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    BSC_STRATEGY_NOT_FOUND("BSC_STRATEGY_NOT_FOUND", "BSC strategy not found", HttpStatus.NOT_FOUND),
    BSC_STRATEGY_NOT_DRAFT("BSC_STRATEGY_NOT_DRAFT", "BSC strategy must be draft", HttpStatus.BAD_REQUEST),
    STEP_LOCKED("STEP_LOCKED", "Step is locked", HttpStatus.BAD_REQUEST),
    STEP_NOT_COMPLETED("STEP_NOT_COMPLETED", "Previous step is not completed", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}