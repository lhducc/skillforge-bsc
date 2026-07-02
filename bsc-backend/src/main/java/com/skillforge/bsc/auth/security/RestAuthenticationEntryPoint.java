package com.skillforge.bsc.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillforge.bsc.common.exception.ErrorCode;
import com.skillforge.bsc.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String ERROR_CODE_ATTRIBUTE = RestAuthenticationEntryPoint.class.getName() + ".errorCode";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ErrorCode errorCode = request.getAttribute(ERROR_CODE_ATTRIBUTE) instanceof ErrorCode requestedCode
                ? requestedCode
                : ErrorCode.AUTH_UNAUTHORIZED;
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }
}
