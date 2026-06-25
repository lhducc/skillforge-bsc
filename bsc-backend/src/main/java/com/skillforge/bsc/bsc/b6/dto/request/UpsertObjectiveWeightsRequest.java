package com.skillforge.bsc.bsc.b6.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpsertObjectiveWeightsRequest {

    @NotNull
    private List<@Valid @NotNull ObjectiveWeightItemRequest> items;
}
