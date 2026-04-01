package com.alam.hotels.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
}
