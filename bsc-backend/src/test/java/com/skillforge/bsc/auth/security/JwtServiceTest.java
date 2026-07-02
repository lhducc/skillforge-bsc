package com.skillforge.bsc.auth.security;

import com.skillforge.bsc.common.enums.UserRole;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String SECRET = "c2tpbGxmb3JnZS10ZXN0LWp3dC1zZWNyZXQta2V5LTIwMjY=";

    @Test
    void generatesAndValidatesToken() {
        JwtService jwtService = new JwtService(SECRET, 86_400);
        UUID accountId = UUID.randomUUID();
        CurrentUser user = new CurrentUser(accountId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                "ceo@company.com", "CEO", UserRole.CEO);

        String token = jwtService.generateToken(user);

        assertThat(jwtService.extractUserAccountId(token)).isEqualTo(accountId);
        assertThat(jwtService.getExpirationSeconds()).isEqualTo(86_400);
    }

    @Test
    void rejectsTamperedToken() {
        JwtService jwtService = new JwtService(SECRET, 86_400);
        CurrentUser user = new CurrentUser(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                "ceo@company.com", "CEO", UserRole.CEO);
        String token = jwtService.generateToken(user);

        assertThatThrownBy(() -> jwtService.extractUserAccountId(token + "tampered"))
                .isInstanceOf(JwtException.class);
    }
}
