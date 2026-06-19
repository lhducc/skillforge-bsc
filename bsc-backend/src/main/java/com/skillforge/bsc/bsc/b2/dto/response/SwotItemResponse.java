package com.skillforge.bsc.bsc.b2.dto.response;

import com.skillforge.bsc.common.enums.AnalysisModelType;
import com.skillforge.bsc.common.enums.SwotType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SwotItemResponse {

    private UUID id;
    private SwotType swotType;
    private UUID sourceAnalysisItemId;
    private AnalysisModelType sourceModelType;
    private String sourceFactorCode;
    private String contentSnapshot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
