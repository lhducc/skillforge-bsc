package com.skillforge.bsc.auth.service;

import com.skillforge.bsc.auth.dto.request.LoginRequest;
import com.skillforge.bsc.auth.dto.response.LoginResponse;
import com.skillforge.bsc.auth.entity.UserAccount;
import com.skillforge.bsc.auth.repository.UserAccountRepository;
import com.skillforge.bsc.auth.security.JwtService;
import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.common.enums.UserAccountStatus;
import com.skillforge.bsc.common.enums.UserRole;
import com.skillforge.bsc.common.exception.BusinessException;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.user.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private static final String SECRET = "c2tpbGxmb3JnZS10ZXN0LWp3dC1zZWNyZXQta2V5LTIwMjY=";

    @Mock
    private UserAccountRepository userAccountRepository;

    private AuthenticationService authenticationService;
    private UserAccount account;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authenticationService = new AuthenticationService(
                userAccountRepository,
                passwordEncoder,
                new JwtService(SECRET, 86_400)
        );
        account = activeAccount(passwordEncoder);
    }

    @Test
    void loginReturnsTokenAndProfileForActiveAccount() {
        when(userAccountRepository.findByEmailIgnoreCase("ceo@company.com")).thenReturn(Optional.of(account));
        when(userAccountRepository.save(any(UserAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LoginResponse response = authenticationService.login(loginRequest(" CEO@Company.com ", "secret123"));

        assertThat(response.getAccessToken()).isNotBlank();
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getExpiresIn()).isEqualTo(86_400);
        assertThat(response.getUser().getUserAccountId()).isEqualTo(account.getId());
        assertThat(response.getUser().getRole()).isEqualTo(UserRole.CEO);
        assertThat(account.getLastLoginAt()).isNotNull();
    }

    @Test
    void loginRejectsWrongPassword() {
        when(userAccountRepository.findByEmailIgnoreCase("ceo@company.com")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> authenticationService.login(loginRequest("ceo@company.com", "wrong")))
                .isInstanceOfSatisfying(BusinessException.class,
                        ex -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.AUTH_INVALID_CREDENTIALS));
    }

    @Test
    void loginRejectsLockedAccount() {
        account.setStatus(UserAccountStatus.LOCKED);
        when(userAccountRepository.findByEmailIgnoreCase("ceo@company.com")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> authenticationService.login(loginRequest("ceo@company.com", "secret123")))
                .isInstanceOfSatisfying(BusinessException.class,
                        ex -> assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.AUTH_ACCOUNT_DISABLED));
    }

    private UserAccount activeAccount(PasswordEncoder passwordEncoder) {
        Company company = new Company();
        company.setId(UUID.randomUUID());
        Department department = new Department();
        department.setId(UUID.randomUUID());
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setCompany(company);
        employee.setDepartment(department);
        employee.setFullName("Nguyen Van CEO");
        employee.setStatus(EmployeeStatus.ACTIVE);

        UserAccount userAccount = new UserAccount();
        userAccount.setId(UUID.randomUUID());
        userAccount.setEmployee(employee);
        userAccount.setEmail("ceo@company.com");
        userAccount.setPasswordHash(passwordEncoder.encode("secret123"));
        userAccount.setRole(UserRole.CEO);
        userAccount.setStatus(UserAccountStatus.ACTIVE);
        return userAccount;
    }

    private LoginRequest loginRequest(String email, String password) {
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }
}
