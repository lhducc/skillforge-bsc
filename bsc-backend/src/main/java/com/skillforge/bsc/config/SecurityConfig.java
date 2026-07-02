package com.skillforge.bsc.config;

import com.skillforge.bsc.auth.security.JwtAuthenticationFilter;
import com.skillforge.bsc.auth.security.RestAccessDeniedHandler;
import com.skillforge.bsc.auth.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/health",
                                "/auth/login",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/auth/me").authenticated()

                        // Company setup is managed by COMPANY_ADMIN.
                        .requestMatchers(HttpMethod.POST, "/companies/*/bsc-strategies").hasRole("CEO")
                        .requestMatchers("/companies/*/departments", "/companies/*/employees",
                                "/departments/**", "/employees/**").hasRole("COMPANY_ADMIN")
                        .requestMatchers("/companies", "/companies/*").hasRole("COMPANY_ADMIN")

                        // CEO owns B1-B4 and B6-B7 setup operations.
                        .requestMatchers("/bsc-strategies/*/assessment/**",
                                "/bsc-strategies/*/analysis-items",
                                "/bsc-strategies/*/swot-items",
                                "/bsc-strategies/*/candidate-strategies",
                                "/bsc-strategies/*/strategy-building/complete",
                                "/swot-items/**", "/candidate-strategies/**",
                                "/bsc-strategies/*/selected-strategies",
                                "/bsc-strategies/*/strategy-result/complete",
                                "/bsc-strategies/*/strategy-maps",
                                "/strategy-maps/**", "/strategic-objectives/**", "/objective-links/**",
                                "/bsc-strategies/*/final-objectives/build",
                                "/bsc-strategies/*/final-objective-links",
                                "/final-objective-links/**",
                                "/bsc-strategies/*/final-strategy-map",
                                "/bsc-strategies/*/strategy-map/complete",
                                "/bsc-strategies/*/weights/**",
                                "/department-kpis/*/measurement",
                                "/bsc-strategies/*/measurements/**").hasRole("CEO")

                        // B5 reads: CEO sees company data; department heads see department data.
                        .requestMatchers(HttpMethod.GET, "/bsc-strategies/*/fishbone/company").hasRole("CEO")
                        .requestMatchers(HttpMethod.GET, "/bsc-strategies/*/fishbone/departments/*")
                                .hasAnyRole("CEO", "DEPARTMENT_HEAD")
                        .requestMatchers("/bsc-strategies/*/department-participations",
                                "/department-participations/**",
                                "/department-kpis/**",
                                "/bsc-strategies/*/fishbone/complete").hasRole("DEPARTMENT_HEAD")

                        // B8 reads are available to CEO and department heads; employees get task views/status updates.
                        .requestMatchers(HttpMethod.GET,
                                "/bsc-strategies/*/action-plans",
                                "/bsc-strategies/*/kpi-reports",
                                "/department-kpis/*/reports").hasAnyRole("CEO", "DEPARTMENT_HEAD")
                        .requestMatchers(HttpMethod.GET, "/bsc-strategies/*/tasks/kanban")
                                .hasAnyRole("CEO", "DEPARTMENT_HEAD", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/bsc-strategies/*/tasks/gantt")
                                .hasAnyRole("CEO", "DEPARTMENT_HEAD")
                        .requestMatchers(HttpMethod.PATCH, "/tasks/*/status")
                                .hasAnyRole("DEPARTMENT_HEAD", "EMPLOYEE")
                        .requestMatchers("/tasks", "/action-plans/**", "/kpi-reports/**",
                                "/bsc-strategies/*/action-plan/complete").hasRole("DEPARTMENT_HEAD")

                        // Dashboard and workflow summary reads use the same coarse strategic visibility.
                        .requestMatchers(HttpMethod.GET, "/bsc-strategies/*/dashboard/**",
                                "/bsc-strategies/*/steps", "/bsc-strategies/*")
                                .hasAnyRole("CEO", "DEPARTMENT_HEAD")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsConfig.getAllowedOrigins());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
