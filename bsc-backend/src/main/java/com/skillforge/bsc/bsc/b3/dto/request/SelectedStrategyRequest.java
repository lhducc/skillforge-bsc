package com.skillforge.bsc.bsc.b3.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SelectedStrategyRequest {

    @NotNull
    private UUID candidateStrategyId;

    @NotNull
    private Integer priorityOrder;

    private String selectionReason;
}
