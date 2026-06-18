package com.skillforge.bsc.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    COMPANY_NOT_FOUND("COMPANY_NOT_FOUND", "Company not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND("DEPARTMENT_NOT_FOUND", "Department not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_CODE_DUPLICATED("DEPARTMENT_CODE_DUPLICATED", "Department code already exists in company", HttpStatus.BAD_REQUEST),
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND", "Employee not found", HttpStatus.NOT_FOUND),
    USER_ACCOUNT_NOT_FOUND("USER_ACCOUNT_NOT_FOUND", "User account not found", HttpStatus.NOT_FOUND),
    USER_ACCOUNT_EMAIL_DUPLICATED("USER_ACCOUNT_EMAIL_DUPLICATED", "User account email already exists", HttpStatus.BAD_REQUEST),

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
