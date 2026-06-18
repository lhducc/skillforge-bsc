package com.skillforge.bsc.company.mapper;

import com.skillforge.bsc.company.dto.response.CompanyResponse;
import com.skillforge.bsc.company.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .taxCode(company.getTaxCode())
                .industry(company.getIndustry())
                .size(company.getSize())
                .status(company.getStatus())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
