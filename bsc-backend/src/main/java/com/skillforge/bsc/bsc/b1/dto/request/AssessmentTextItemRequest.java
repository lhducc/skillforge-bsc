package com.skillforge.bsc.bsc.b1.dto.request;

import com.skillforge.bsc.common.enums.AssessmentTextItemCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentTextItemRequest {

    @NotNull
    private AssessmentTextItemCategory category;

    @NotBlank
    private String content;

    private Integer displayOrder;
}
