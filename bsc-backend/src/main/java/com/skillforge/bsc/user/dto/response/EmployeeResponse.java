package com.skillforge.bsc.user.dto.response;

import com.skillforge.bsc.common.enums.EmployeeStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class EmployeeResponse {

    private UUID id;
    private UUID companyId;
    private UUID departmentId;
    private String fullName;
    private String email;
    private String phone;
    private String positionTitle;
    private EmployeeStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
