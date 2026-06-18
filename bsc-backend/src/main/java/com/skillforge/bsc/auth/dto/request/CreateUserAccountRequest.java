package com.skillforge.bsc.auth.dto.request;

import com.skillforge.bsc.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserAccountRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;
}
