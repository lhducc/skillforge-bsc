package com.skillforge.bsc.auth.security;

import com.skillforge.bsc.auth.entity.UserAccount;
import com.skillforge.bsc.auth.repository.UserAccountRepository;
import com.skillforge.bsc.common.enums.EmployeeStatus;
import com.skillforge.bsc.common.enums.UserAccountStatus;
import com.skillforge.bsc.common.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorization.substring(BEARER_PREFIX.length()).trim();
            UUID userAccountId = jwtService.extractUserAccountId(token);
            UserAccount account = userAccountRepository.findWithEmployeeById(userAccountId)
                    .orElseThrow(TokenInvalidAuthenticationException::new);
            if (!isActive(account)) {
                throw new AccountDisabledAuthenticationException();
            }
            CurrentUser currentUser = CurrentUser.from(account);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    currentUser,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + currentUser.role().name()))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            reject(request, response, ErrorCode.AUTH_TOKEN_EXPIRED);
        } catch (AccountDisabledAuthenticationException ex) {
            reject(request, response, ErrorCode.AUTH_ACCOUNT_DISABLED);
        } catch (TokenInvalidAuthenticationException | JwtException | IllegalArgumentException ex) {
            reject(request, response, ErrorCode.AUTH_TOKEN_INVALID);
        }
    }

    private boolean isActive(UserAccount account) {
        return account.getStatus() == UserAccountStatus.ACTIVE
                && account.getEmployee().getStatus() == EmployeeStatus.ACTIVE;
    }

    private void reject(HttpServletRequest request, HttpServletResponse response, ErrorCode errorCode) throws IOException {
        SecurityContextHolder.clearContext();
        request.setAttribute(RestAuthenticationEntryPoint.ERROR_CODE_ATTRIBUTE, errorCode);
        authenticationEntryPoint.commence(request, response, new AccountDisabledAuthenticationException());
    }

    private static final class AccountDisabledAuthenticationException
            extends org.springframework.security.core.AuthenticationException {
        private AccountDisabledAuthenticationException() {
            super("Authentication failed");
        }
    }

    private static final class TokenInvalidAuthenticationException
            extends org.springframework.security.core.AuthenticationException {
        private TokenInvalidAuthenticationException() {
            super("Authentication token does not identify an account");
        }
    }
}
