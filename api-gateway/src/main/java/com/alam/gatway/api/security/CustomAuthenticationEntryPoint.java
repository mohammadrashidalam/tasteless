package com.alam.gatway.api.security;

import com.alam.gatway.api.enums.ErrorType;
import com.alam.gatway.api.payload.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint  implements ServerAuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

        log.info("CustomAuthenticationEntryPoint-Gateway Service :: commence ",ex);
        ErrorType errorType = ErrorType.UNAUTHORIZED;

        if (ex instanceof InvalidBearerTokenException) {
            errorType = ErrorType.INVALID_TOKEN;
        } else if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("expired")) {
            errorType = ErrorType.TOKEN_EXPIRED;
        }

        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .status(errorType.getStatus().value())
                .error(errorType.getStatus().getReasonPhrase())
                .message(errorType.getMessage())
                .timestamp(Instant.now())
                .build();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorType.getStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = null;
        try {
            bytes =mapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }
}
