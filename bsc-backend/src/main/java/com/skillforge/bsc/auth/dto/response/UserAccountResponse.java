package com.skillforge.bsc.auth.dto.response;

import com.skillforge.bsc.common.enums.UserAccountStatus;
import com.skillforge.bsc.common.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserAccountResponse {

    private UUID id;
    private UUID employeeId;
    private String email;
    private UserRole role;
    private UserAccountStatus status;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
