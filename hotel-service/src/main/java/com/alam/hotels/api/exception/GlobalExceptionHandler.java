package com.alam.hotels.api.exception;

import com.alam.hotels.api.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException
            (ResourceNotFoundException ex) {
        String message = ex.getMessage();
        APIResponse apiResponse = APIResponse.builder()
                .message(message)
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
