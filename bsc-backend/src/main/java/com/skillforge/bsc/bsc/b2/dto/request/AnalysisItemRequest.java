package com.skillforge.bsc.bsc.b2.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnalysisItemRequest {

    private UUID id;

    @NotBlank
    private String modelType;

    @NotBlank
    private String factorCode;

    @NotBlank
    private String content;

    private Integer displayOrder;
}
