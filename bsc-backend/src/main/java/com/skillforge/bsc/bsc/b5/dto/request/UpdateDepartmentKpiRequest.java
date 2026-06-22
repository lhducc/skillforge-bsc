package com.skillforge.bsc.bsc.b5.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDepartmentKpiRequest {

    @NotNull
    private UUID bscStrategyId;

    @NotNull
    private UUID finalStrategicObjectiveId;

    @NotNull
    private UUID departmentId;

    @NotNull
    private UUID departmentParticipationId;

    @NotBlank
    private String name;

    private String description;

    private Integer displayOrder;
}
