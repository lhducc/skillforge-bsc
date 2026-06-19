package com.skillforge.bsc.bsc.b1.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpsertAssessmentFinancialsRequest {

    @NotNull
    private List<@Valid @NotNull AssessmentFinancialItemRequest> items;
}
