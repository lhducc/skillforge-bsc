package com.skillforge.bsc.bsc.b4.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateStrategicObjectiveRequest {

    @NotNull
    private UUID selectedStrategyId;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String perspectiveCode;

    private Integer displayOrder;
}
