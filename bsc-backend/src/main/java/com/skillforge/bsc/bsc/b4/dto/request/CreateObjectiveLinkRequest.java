package com.skillforge.bsc.bsc.b4.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateObjectiveLinkRequest {

    @NotNull
    private UUID sourceObjectiveId;

    @NotNull
    private UUID targetObjectiveId;

    private String note;

    private Integer displayOrder;
}
