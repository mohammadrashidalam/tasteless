package com.alam.users.api.security;


import com.alam.users.api.enums.ErrorType;
import com.alam.users.api.payload.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private  ObjectMapper objectMapper ;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        log.info("CustomAuthenticationEntryPoint(User Service) :: commence");

        ErrorType errorType = ErrorType.UNAUTHORIZED;

        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("expired")) {
            errorType = ErrorType.TOKEN_EXPIRED;
        }

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .status(errorType.getStatus().value())
                .error(errorType.getStatus().getReasonPhrase())
                .message(errorType.getMessage())
                .timestamp(Instant.now())
                .build();

        response.setStatus(errorType.getStatus().value());
        response.setContentType("application/json");

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
