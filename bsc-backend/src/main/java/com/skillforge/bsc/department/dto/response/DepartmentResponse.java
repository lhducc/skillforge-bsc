package com.skillforge.bsc.department.dto.response;

import com.skillforge.bsc.common.enums.DepartmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DepartmentResponse {

    private UUID id;
    private UUID companyId;
    private String name;
    private String code;
    private String color;
    private String description;
    private DepartmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
