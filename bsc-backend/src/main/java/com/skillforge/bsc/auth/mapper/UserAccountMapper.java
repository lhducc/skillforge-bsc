package com.skillforge.bsc.auth.mapper;

import com.skillforge.bsc.auth.dto.response.UserAccountResponse;
import com.skillforge.bsc.auth.entity.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper {

    public UserAccountResponse toResponse(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .id(userAccount.getId())
                .employeeId(userAccount.getEmployee().getId())
                .email(userAccount.getEmail())
                .role(userAccount.getRole())
                .status(userAccount.getStatus())
                .lastLoginAt(userAccount.getLastLoginAt())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .build();
    }
}
