package com.alam.rating.api.payload;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private Instant timestamp;
}
