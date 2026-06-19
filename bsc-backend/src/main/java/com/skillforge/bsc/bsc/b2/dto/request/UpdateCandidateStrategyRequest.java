package com.skillforge.bsc.bsc.b2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateCandidateStrategyRequest {

    @NotBlank
    private String strategyGroup;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private List<@NotNull UUID> swotItemIds;

    private Integer displayOrder;
}
