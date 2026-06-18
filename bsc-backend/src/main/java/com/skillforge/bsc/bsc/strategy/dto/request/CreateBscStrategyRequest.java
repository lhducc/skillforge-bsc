package com.skillforge.bsc.bsc.strategy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBscStrategyRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer year;
}
