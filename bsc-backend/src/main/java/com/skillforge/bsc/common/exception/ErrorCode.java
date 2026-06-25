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
    B2_CANDIDATE_STRATEGY_REQUIRED("B2_CANDIDATE_STRATEGY_REQUIRED", "At least one active candidate strategy is required", HttpStatus.BAD_REQUEST),

    B3_SELECTED_STRATEGY_COUNT_INVALID("B3_SELECTED_STRATEGY_COUNT_INVALID", "Selected strategy count must be between 1 and 2", HttpStatus.BAD_REQUEST),
    B3_SELECTED_STRATEGY_DUPLICATED("B3_SELECTED_STRATEGY_DUPLICATED", "Selected strategies must not be duplicated", HttpStatus.BAD_REQUEST),
    B3_CANDIDATE_STRATEGY_INVALID("B3_CANDIDATE_STRATEGY_INVALID", "Candidate strategy is invalid for B3 selection", HttpStatus.BAD_REQUEST),
    B3_PRIORITY_ORDER_INVALID("B3_PRIORITY_ORDER_INVALID", "Priority order must be 1 or 2", HttpStatus.BAD_REQUEST),
    B3_PRIORITY_ORDER_DUPLICATED("B3_PRIORITY_ORDER_DUPLICATED", "Priority order must not be duplicated", HttpStatus.BAD_REQUEST),

    B4_SELECTED_STRATEGY_INVALID("B4_SELECTED_STRATEGY_INVALID", "Selected strategy is invalid for B4", HttpStatus.BAD_REQUEST),
    B4_STRATEGY_MAP_NOT_FOUND("B4_STRATEGY_MAP_NOT_FOUND", "Strategy map not found", HttpStatus.NOT_FOUND),
    B4_STRATEGY_MAP_DUPLICATED("B4_STRATEGY_MAP_DUPLICATED", "Strategy map already exists for selected strategy", HttpStatus.BAD_REQUEST),
    B4_STRATEGY_MAP_TYPE_INVALID("B4_STRATEGY_MAP_TYPE_INVALID", "Strategy map type is invalid", HttpStatus.BAD_REQUEST),
    B4_OBJECTIVE_NOT_FOUND("B4_OBJECTIVE_NOT_FOUND", "Strategic objective not found", HttpStatus.NOT_FOUND),
    B4_OBJECTIVE_NAME_REQUIRED("B4_OBJECTIVE_NAME_REQUIRED", "Objective name must not be blank", HttpStatus.BAD_REQUEST),
    B4_OBJECTIVE_PERSPECTIVE_REQUIRED("B4_OBJECTIVE_PERSPECTIVE_REQUIRED", "Objective perspective is required or invalid", HttpStatus.BAD_REQUEST),
    B4_OBJECTIVE_LIMIT_EXCEEDED("B4_OBJECTIVE_LIMIT_EXCEEDED", "Objective limit exceeded", HttpStatus.BAD_REQUEST),
    B4_MISSING_PERSPECTIVE("B4_MISSING_PERSPECTIVE", "Strategy map must include all 4 BSC perspectives", HttpStatus.BAD_REQUEST),
    B4_OBJECTIVE_LINK_NOT_FOUND("B4_OBJECTIVE_LINK_NOT_FOUND", "Objective link not found", HttpStatus.NOT_FOUND),
    B4_OBJECTIVE_LINK_SELF_REFERENCE("B4_OBJECTIVE_LINK_SELF_REFERENCE", "Objective link cannot reference itself", HttpStatus.BAD_REQUEST),
    B4_OBJECTIVE_LINK_DUPLICATED("B4_OBJECTIVE_LINK_DUPLICATED", "Objective link is duplicated", HttpStatus.BAD_REQUEST),
    B4_OBJECTIVE_LINK_INVALID("B4_OBJECTIVE_LINK_INVALID", "Objective link is invalid", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_REQUIRED("B4_FINAL_OBJECTIVE_REQUIRED", "Final objective is required", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_NOT_FOUND("B4_FINAL_OBJECTIVE_NOT_FOUND", "Final objective not found", HttpStatus.NOT_FOUND),
    B4_FINAL_OBJECTIVE_SOURCE_INVALID("B4_FINAL_OBJECTIVE_SOURCE_INVALID", "Final objective source is invalid", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_SOURCE_DUPLICATED("B4_FINAL_OBJECTIVE_SOURCE_DUPLICATED", "Source objective must not be reused across final objectives", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_COUNT_INVALID("B4_FINAL_OBJECTIVE_COUNT_INVALID", "Final objective count is invalid", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_LINK_NOT_FOUND("B4_FINAL_OBJECTIVE_LINK_NOT_FOUND", "Final objective link not found", HttpStatus.NOT_FOUND),
    B4_FINAL_OBJECTIVE_LINK_SELF_REFERENCE("B4_FINAL_OBJECTIVE_LINK_SELF_REFERENCE", "Final objective link cannot reference itself", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_LINK_DUPLICATED("B4_FINAL_OBJECTIVE_LINK_DUPLICATED", "Final objective link is duplicated", HttpStatus.BAD_REQUEST),
    B4_FINAL_OBJECTIVE_LINK_INVALID("B4_FINAL_OBJECTIVE_LINK_INVALID", "Final objective link is invalid", HttpStatus.BAD_REQUEST),

    B5_DEPARTMENT_ALREADY_JOINED_OBJECTIVE("B5_DEPARTMENT_ALREADY_JOINED_OBJECTIVE", "Department already joined final objective", HttpStatus.BAD_REQUEST),
    B5_OBJECTIVE_NOT_FOUND("B5_OBJECTIVE_NOT_FOUND", "Final strategic objective not found for B5", HttpStatus.NOT_FOUND),
    B5_DEPARTMENT_NOT_FOUND("B5_DEPARTMENT_NOT_FOUND", "Department not found for B5", HttpStatus.NOT_FOUND),
    B5_PARTICIPATION_NOT_FOUND("B5_PARTICIPATION_NOT_FOUND", "Department participation not found", HttpStatus.NOT_FOUND),
    B5_PARTICIPATION_HAS_ACTIVE_KPIS("B5_PARTICIPATION_HAS_ACTIVE_KPIS", "Department participation still has active KPIs", HttpStatus.BAD_REQUEST),
    B5_KPI_NOT_FOUND("B5_KPI_NOT_FOUND", "Department KPI not found", HttpStatus.NOT_FOUND),
    B5_KPI_NAME_REQUIRED("B5_KPI_NAME_REQUIRED", "Department KPI name must not be blank", HttpStatus.BAD_REQUEST),
    B5_KPI_MUST_BELONG_TO_PARTICIPATION("B5_KPI_MUST_BELONG_TO_PARTICIPATION", "Department KPI must belong to a valid participation", HttpStatus.BAD_REQUEST),
    B5_KPI_DUPLICATED("B5_KPI_DUPLICATED", "Department KPI is duplicated", HttpStatus.BAD_REQUEST),
    B5_DEPARTMENT_HEAD_INVALID("B5_DEPARTMENT_HEAD_INVALID", "Department head is invalid for department participation", HttpStatus.BAD_REQUEST),
    B5_FINAL_OBJECTIVE_REQUIRED("B5_FINAL_OBJECTIVE_REQUIRED", "At least one final strategic objective is required for B5", HttpStatus.BAD_REQUEST),
    B5_PARTICIPATION_REQUIRED("B5_PARTICIPATION_REQUIRED", "Each final objective must have at least one active department participation", HttpStatus.BAD_REQUEST),
    B5_KPI_REQUIRED("B5_KPI_REQUIRED", "Each final objective and participation must have at least one active department KPI", HttpStatus.BAD_REQUEST),

    B6_PERSPECTIVE_WEIGHT_MISSING("B6_PERSPECTIVE_WEIGHT_MISSING", "All 4 BSC perspectives must have weights", HttpStatus.BAD_REQUEST),
    B6_TOTAL_PERSPECTIVE_WEIGHT_INVALID("B6_TOTAL_PERSPECTIVE_WEIGHT_INVALID", "Total perspective weight must equal 100", HttpStatus.BAD_REQUEST),
    B6_OBJECTIVE_WEIGHT_MISSING("B6_OBJECTIVE_WEIGHT_MISSING", "Every active final strategic objective must have one weight", HttpStatus.BAD_REQUEST),
    B6_OBJECTIVE_WEIGHT_TOTAL_INVALID("B6_OBJECTIVE_WEIGHT_TOTAL_INVALID", "Objective weights in each perspective must equal the perspective weight", HttpStatus.BAD_REQUEST),
    B6_KPI_WEIGHT_MISSING("B6_KPI_WEIGHT_MISSING", "Every active department KPI must have one weight", HttpStatus.BAD_REQUEST),
    B6_KPI_WEIGHT_TOTAL_INVALID("B6_KPI_WEIGHT_TOTAL_INVALID", "KPI weights under each final objective must equal the objective weight", HttpStatus.BAD_REQUEST),
    B6_WEIGHT_MUST_BE_POSITIVE("B6_WEIGHT_MUST_BE_POSITIVE", "Weight must be greater than 0", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
