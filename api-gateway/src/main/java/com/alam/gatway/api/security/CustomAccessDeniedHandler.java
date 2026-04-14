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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
@Slf4j
@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.info("CustomAccessDeniedHandler(Gateway Service) :: handle");
        ErrorType errorType = ErrorType.FORBIDDEN;

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
