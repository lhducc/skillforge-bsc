package com.skillforge.bsc.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDepartmentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private String color;

    private String description;
}
