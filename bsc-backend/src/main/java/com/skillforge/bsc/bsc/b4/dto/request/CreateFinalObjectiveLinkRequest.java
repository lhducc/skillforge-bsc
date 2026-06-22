package com.skillforge.bsc.bsc.b4.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateFinalObjectiveLinkRequest {

    @NotNull
    private UUID sourceFinalObjectiveId;

    @NotNull
    private UUID targetFinalObjectiveId;

    private String note;

    private Integer displayOrder;
}
