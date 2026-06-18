package com.skillforge.bsc.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCompanyRequest {

    @NotBlank
    private String name;

    private String taxCode;

    private String industry;

    private String size;
}
