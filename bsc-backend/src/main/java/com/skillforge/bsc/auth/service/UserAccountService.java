package com.skillforge.bsc.auth.service;

import com.skillforge.bsc.auth.dto.request.CreateUserAccountRequest;
import com.skillforge.bsc.auth.dto.response.UserAccountResponse;
import com.skillforge.bsc.auth.entity.UserAccount;
import com.skillforge.bsc.auth.mapper.UserAccountMapper;
import com.skillforge.bsc.auth.repository.UserAccountRepository;
import com.skillforge.bsc.common.enums.UserAccountStatus;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.user.entity.Employee;
import com.skillforge.bsc.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final EmployeeService employeeService;
    private final UserAccountMapper userAccountMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserAccountResponse create(UUID employeeId, CreateUserAccountRequest request) {
        Employee employee = employeeService.getEmployee(employeeId);
        String email = normalizeRequired(request.getEmail(), "email");
        if (userAccountRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.USER_ACCOUNT_EMAIL_DUPLICATED);
        }
        if (userAccountRepository.findByEmployeeId(employeeId).isPresent()) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "employee already has a user account");
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setEmployee(employee);
        userAccount.setEmail(email);
        userAccount.setPasswordHash(passwordEncoder.encode(normalizeRequired(request.getPassword(), "password")));
        userAccount.setRole(request.getRole());
        userAccount.setStatus(UserAccountStatus.ACTIVE);

        return userAccountMapper.toResponse(userAccountRepository.save(userAccount));
    }

    @Transactional(readOnly = true)
    public UserAccountResponse getById(UUID userAccountId) {
        return userAccountMapper.toResponse(getUserAccount(userAccountId));
    }

    @Transactional(readOnly = true)
    public UserAccount getUserAccount(UUID userAccountId) {
        return userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_ACCOUNT_NOT_FOUND));
    }

    private String normalizeRequired(String value, String fieldName) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, fieldName + " is required");
        }
        return normalized;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
