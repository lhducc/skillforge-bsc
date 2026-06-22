package com.skillforge.bsc.bsc.b4.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildFinalObjectivesRequest {

    @Valid
    @NotEmpty
    private List<FinalObjectiveBuildItemRequest> items;
}
