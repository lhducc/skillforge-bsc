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
    B1_TEXT_ITEM_EMPTY("B1_TEXT_ITEM_EMPTY", "Text item content must not be blank", HttpStatus.BAD_REQUEST),

    B2_ANALYSIS_ITEM_NOT_FOUND("B2_ANALYSIS_ITEM_NOT_FOUND", "Analysis item not found", HttpStatus.NOT_FOUND),
    B2_ANALYSIS_MODEL_TYPE_INVALID("B2_ANALYSIS_MODEL_TYPE_INVALID", "Analysis model type is invalid", HttpStatus.BAD_REQUEST),
    B2_ANALYSIS_FACTOR_INVALID("B2_ANALYSIS_FACTOR_INVALID", "Factor code is invalid for the selected model type", HttpStatus.BAD_REQUEST),
    B2_ANALYSIS_ITEM_SELECTED("B2_ANALYSIS_ITEM_SELECTED", "Selected analysis item cannot be removed or changed", HttpStatus.BAD_REQUEST),
    B2_SWOT_ITEM_NOT_FOUND("B2_SWOT_ITEM_NOT_FOUND", "SWOT item not found", HttpStatus.NOT_FOUND),
    B2_SWOT_TYPE_INVALID("B2_SWOT_TYPE_INVALID", "SWOT type is invalid", HttpStatus.BAD_REQUEST),
    B2_SWOT_ITEM_DUPLICATED_SOURCE("B2_SWOT_ITEM_DUPLICATED_SOURCE", "Analysis item is already selected into SWOT", HttpStatus.BAD_REQUEST),
    B2_SWOT_ITEM_ALREADY_USED("B2_SWOT_ITEM_ALREADY_USED", "SWOT item is already used by an active candidate strategy", HttpStatus.BAD_REQUEST),
    B2_CANDIDATE_STRATEGY_NOT_FOUND("B2_CANDIDATE_STRATEGY_NOT_FOUND", "Candidate strategy not found", HttpStatus.NOT_FOUND),
    B2_CANDIDATE_STRATEGY_LIMIT_EXCEEDED("B2_CANDIDATE_STRATEGY_LIMIT_EXCEEDED", "Candidate strategy limit exceeded", HttpStatus.BAD_REQUEST),
    B2_STRATEGY_GROUP_INVALID("B2_STRATEGY_GROUP_INVALID", "Strategy group is invalid", HttpStatus.BAD_REQUEST),
    B2_STRATEGY_NAME_REQUIRED("B2_STRATEGY_NAME_REQUIRED", "Candidate strategy name must not be blank", HttpStatus.BAD_REQUEST),
    B2_STRATEGY_SWOT_RULE_INVALID("B2_STRATEGY_SWOT_RULE_INVALID", "Candidate strategy SWOT rule is invalid", HttpStatus.BAD_REQUEST),
    B2_CANDIDATE_STRATEGY_REQUIRED("B2_CANDIDATE_STRATEGY_REQUIRED", "At least one active candidate strategy is required", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
