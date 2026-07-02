package com.skillforge.bsc.auth.security;

import com.skillforge.bsc.auth.dto.response.AuthenticatedUserResponse;
import com.skillforge.bsc.auth.entity.UserAccount;
import com.skillforge.bsc.common.enums.UserRole;

import java.util.UUID;

public record CurrentUser(
        UUID userAccountId,
        UUID employeeId,
        UUID companyId,
        UUID departmentId,
        String email,
        String fullName,
        UserRole role
) {
    public static CurrentUser from(UserAccount account) {
        return new CurrentUser(
                account.getId(),
                account.getEmployee().getId(),
                account.getEmployee().getCompany().getId(),
                account.getEmployee().getDepartment().getId(),
                account.getEmail(),
                account.getEmployee().getFullName(),
                account.getRole()
        );
    }

    public AuthenticatedUserResponse toResponse() {
        return AuthenticatedUserResponse.builder()
                .userAccountId(userAccountId)
                .employeeId(employeeId)
                .companyId(companyId)
                .departmentId(departmentId)
                .email(email)
                .fullName(fullName)
                .role(role)
                .build();
    }
}
