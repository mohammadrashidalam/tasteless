package com.alam.gatway.api.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access Denied");

    private final HttpStatus status;
    private final String message;

    ErrorType(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
