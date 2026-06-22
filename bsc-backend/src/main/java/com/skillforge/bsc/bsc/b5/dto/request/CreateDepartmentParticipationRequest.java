package com.skillforge.bsc.bsc.b5.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDepartmentParticipationRequest {

    @NotNull
    private UUID finalStrategicObjectiveId;

    @NotNull
    private UUID departmentId;

    private UUID departmentHeadId;
}
