package com.skillforge.bsc.department.mapper;

import com.skillforge.bsc.department.dto.response.DepartmentResponse;
import com.skillforge.bsc.department.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .companyId(department.getCompany().getId())
                .name(department.getName())
                .code(department.getCode())
                .color(department.getColor())
                .description(department.getDescription())
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}
