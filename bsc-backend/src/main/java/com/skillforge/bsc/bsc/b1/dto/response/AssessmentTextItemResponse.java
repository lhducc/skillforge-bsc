package com.skillforge.bsc.bsc.b1.dto.response;

import com.skillforge.bsc.common.enums.AssessmentTextItemCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AssessmentTextItemResponse {

    private UUID id;
    private AssessmentTextItemCategory category;
    private String content;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
