package com.skillforge.bsc.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillforge.bsc.auth.dto.request.CreateUserAccountRequest;
import com.skillforge.bsc.auth.entity.UserAccount;
import com.skillforge.bsc.auth.repository.UserAccountRepository;
import com.skillforge.bsc.auth.service.UserAccountService;
import com.skillforge.bsc.common.enums.CompanyStatus;
import com.skillforge.bsc.common.enums.DepartmentStatus;
import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.common.enums.UserRole;
import com.skillforge.bsc.company.entity.Company;
import com.skillforge.bsc.company.repository.CompanyRepository;
import com.skillforge.bsc.department.entity.Department;
import com.skillforge.bsc.department.repository.DepartmentRepository;
import com.skillforge.bsc.user.entity.Employee;
import com.skillforge.bsc.user.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthenticationFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void createdAccountCanLoginAndUseJwt() throws Exception {
        Company company = new Company();
        company.setName("Phase 11 Test Company");
        company.setStatus(CompanyStatus.ACTIVE);
        company = companyRepository.save(company);

        Department department = new Department();
        department.setCompany(company);
        department.setName("Administration");
        department.setCode("ADMIN-" + UUID.randomUUID());
        department.setStatus(DepartmentStatus.ACTIVE);
        department = departmentRepository.save(department);

        String email = "phase11-" + UUID.randomUUID() + "@company.com";
        Employee employee = new Employee();
        employee.setCompany(company);
        employee.setDepartment(department);
        employee.setFullName("Phase 11 Admin");
        employee.setEmail(email);
        employee.setStatus(EmployeeStatus.ACTIVE);
        employee = employeeRepository.save(employee);

        CreateUserAccountRequest accountRequest = new CreateUserAccountRequest();
        accountRequest.setEmail(email);
        accountRequest.setPassword("SecurePass123!");
        accountRequest.setRole(UserRole.COMPANY_ADMIN);
        userAccountService.create(employee.getId(), accountRequest);

        UserAccount account = userAccountRepository.findByEmailIgnoreCase(email).orElseThrow();
        assertThat(account.getPasswordHash()).isNotEqualTo("SecurePass123!");
        assertThat(account.getPasswordHash()).startsWith("$2");
        assertThat(passwordEncoder.matches("SecurePass123!", account.getPasswordHash())).isTrue();

        String loginBody = objectMapper.writeValueAsString(new LoginPayload(email, "SecurePass123!"));
        String responseBody = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.user.role").value("COMPANY_ADMIN"))
                .andExpect(jsonPath("$.data.user.passwordHash").doesNotExist())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseJson = objectMapper.readTree(responseBody);
        String token = responseJson.path("data").path("accessToken").asText();
        assertThat(token).isNotBlank();

        mockMvc.perform(get("/auth/me").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(email));

        mockMvc.perform(get("/companies/{companyId}", company.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(company.getId().toString()));

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.securitySchemes.bearerAuth.scheme").value("bearer"));
    }

    private record LoginPayload(String email, String password) {
    }
}
