package com.alam.users.api.security;


import com.alam.users.api.enums.ErrorType;
import com.alam.users.api.payload.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private  ObjectMapper objectMapper ;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("CustomAccessDeniedHandler(User Service) :: handle");
        ErrorType errorType = ErrorType.FORBIDDEN;

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