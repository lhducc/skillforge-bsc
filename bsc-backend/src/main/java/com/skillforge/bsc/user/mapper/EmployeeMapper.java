package com.skillforge.bsc.user.mapper;

import com.skillforge.bsc.user.dto.response.EmployeeResponse;
import com.skillforge.bsc.user.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .companyId(employee.getCompany().getId())
                .departmentId(employee.getDepartment().getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .positionTitle(employee.getPositionTitle())
                .status(employee.getStatus())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}
