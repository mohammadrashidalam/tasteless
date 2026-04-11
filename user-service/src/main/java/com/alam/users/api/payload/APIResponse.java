package com.alam.users.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
    private Instant timestamp;
}
