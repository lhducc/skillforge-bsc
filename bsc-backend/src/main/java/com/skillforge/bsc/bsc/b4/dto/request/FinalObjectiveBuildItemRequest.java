package com.skillforge.bsc.bsc.b4.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FinalObjectiveBuildItemRequest {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String perspectiveCode;

    @NotBlank
    private String sourceType;

    @NotNull
    private List<@NotNull UUID> sourceObjectiveIds;

    private Integer displayOrder;
}
