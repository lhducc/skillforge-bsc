package com.skillforge.bsc.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource not found", HttpStatus.NOT_FOUND),
    DUPLICATED_RESOURCE("DUPLICATED_RESOURCE", "Duplicated resource", HttpStatus.BAD_REQUEST),
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
    STEP_NOT_COMPLETED("STEP_NOT_COMPLETED", "Previous step is not completed", HttpStatus.BAD_REQUEST),
    STEP_ALREADY_COMPLETED("STEP_ALREADY_COMPLETED", "Step is already completed", HttpStatus.BAD_REQUEST),

    B1_FINANCIAL_REQUIRED("B1_FINANCIAL_REQUIRED", "At least one financial row is required", HttpStatus.BAD_REQUEST),
    B1_FINANCIAL_EXCEED_LIMIT("B1_FINANCIAL_EXCEED_LIMIT", "Financial data must not exceed 3 rows", HttpStatus.BAD_REQUEST),
    B1_REVENUE_NEGATIVE("B1_REVENUE_NEGATIVE", "Revenue must be greater than or equal to 0", HttpStatus.BAD_REQUEST),
    B1_MARKET_SHARE_TOTAL_INVALID("B1_MARKET_SHARE_TOTAL_INVALID", "Market share total must equal 100 for each period", HttpStatus.BAD_REQUEST),
    B1_MARKET_SHARE_OWN_COMPANY_REQUIRED("B1_MARKET_SHARE_OWN_COMPANY_REQUIRED", "Each market share period must include own company", HttpStatus.BAD_REQUEST),
    B1_TEXT_ITEM_EMPTY("B1_TEXT_ITEM_EMPTY", "Text item content must not be blank", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
