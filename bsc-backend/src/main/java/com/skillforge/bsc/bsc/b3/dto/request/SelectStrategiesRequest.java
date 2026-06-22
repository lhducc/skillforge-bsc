package com.skillforge.bsc.bsc.b3.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelectStrategiesRequest {

    @NotNull
    @Size(min = 1, max = 2)
    private List<@Valid @NotNull SelectedStrategyRequest> items;
}
