package com.skillforge.bsc.config;

import com.skillforge.bsc.auth.repository.UserAccountRepository;
import com.skillforge.bsc.auth.security.JwtAuthenticationFilter;
import com.skillforge.bsc.auth.security.JwtService;
import com.skillforge.bsc.auth.security.RestAccessDeniedHandler;
import com.skillforge.bsc.auth.security.RestAuthenticationEntryPoint;
import com.skillforge.bsc.company.controller.CompanyController;
import com.skillforge.bsc.company.service.CompanyService;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@Import({SecurityConfig.class, CorsConfig.class, JwtAuthenticationFilter.class,
        RestAuthenticationEntryPoint.class, RestAccessDeniedHandler.class})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompanyService companyService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserAccountRepository userAccountRepository;

    @Test
    void protectedEndpointWithoutTokenIsUnauthorized() throws Exception {
        mockMvc.perform(get("/companies/{companyId}", UUID.randomUUID()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH_UNAUTHORIZED"));
    }

    @Test
    void protectedEndpointWithInvalidTokenIsUnauthorized() throws Exception {
        when(jwtService.extractUserAccountId(anyString())).thenThrow(new JwtException("invalid"));

        mockMvc.perform(get("/companies/{companyId}", UUID.randomUUID())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH_TOKEN_INVALID"));
    }

    @Test
    void validRoleCanAccessRestrictedEndpoint() throws Exception {
        mockMvc.perform(get("/companies/{companyId}", UUID.randomUUID())
                        .with(user("admin").roles("COMPANY_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void invalidRoleIsForbidden() throws Exception {
        mockMvc.perform(get("/companies/{companyId}", UUID.randomUUID())
                        .with(user("employee").roles("EMPLOYEE")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("AUTH_ACCESS_DENIED"));
    }

    @Test
    void configuredLocalOriginPassesCorsPreflight() throws Exception {
        mockMvc.perform(options("/companies/{companyId}", UUID.randomUUID())
                        .header(HttpHeaders.ORIGIN, "http://localhost:5173")
                        .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET.name()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:5173"));
    }
}
