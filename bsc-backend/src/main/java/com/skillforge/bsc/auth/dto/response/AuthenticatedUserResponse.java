package com.skillforge.bsc.auth.dto.response;

import com.skillforge.bsc.common.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AuthenticatedUserResponse {

    private UUID userAccountId;
    private UUID employeeId;
    private UUID companyId;
    private UUID departmentId;
    private String email;
    private String fullName;
    private UserRole role;
}
