package com.skillforge.bsc.bsc.b2.dto.response;

import com.skillforge.bsc.common.enums.AnalysisModelType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AnalysisItemResponse {

    private UUID id;
    private AnalysisModelType modelType;
    private String factorCode;
    private String content;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
