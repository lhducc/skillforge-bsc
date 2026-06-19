package com.skillforge.bsc.bsc.b2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateSwotItemRequest {

    @NotBlank
    private String swotType;

    @NotNull
    private UUID sourceAnalysisItemId;
}
