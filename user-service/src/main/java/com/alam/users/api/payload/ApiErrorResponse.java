package com.alam.users.api.payload;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private Instant timestamp;
}
