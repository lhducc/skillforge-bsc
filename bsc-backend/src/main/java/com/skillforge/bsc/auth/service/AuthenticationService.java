package com.skillforge.bsc.auth.service;

import com.skillforge.bsc.auth.dto.request.LoginRequest;
import com.skillforge.bsc.auth.dto.response.LoginResponse;
import com.skillforge.bsc.auth.entity.UserAccount;
import com.skillforge.bsc.auth.repository.UserAccountRepository;
import com.skillforge.bsc.auth.security.CurrentUser;
import com.skillforge.bsc.auth.security.JwtService;
import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.common.enums.UserAccountStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);
        UserAccount account = userAccountRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), account.getPasswordHash())) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }
        if (account.getStatus() != UserAccountStatus.ACTIVE
                || account.getEmployee().getStatus() != EmployeeStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.AUTH_ACCOUNT_DISABLED);
        }

        account.setLastLoginAt(LocalDateTime.now());
        userAccountRepository.save(account);
        CurrentUser currentUser = CurrentUser.from(account);
        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(currentUser))
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationSeconds())
                .user(currentUser.toResponse())
                .build();
    }
}
