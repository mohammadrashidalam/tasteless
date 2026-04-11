package com.alam.users.api.exception;

import com.alam.users.api.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException
            (ResourceNotFoundException ex) {
        String message = ex.getMessage();
        APIResponse apiResponse = APIResponse.builder()
                .message(message)
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<APIResponse> handleServiceUnavailableException
            (ServiceUnavailableException ex) {
        String message = ex.getMessage();
        APIResponse apiResponse = APIResponse.builder()
                .message(message)
                .success(false)
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
