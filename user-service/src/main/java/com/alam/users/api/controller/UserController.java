package com.alam.users.api.controller;

import com.alam.users.api.dto.UserDto;
import com.alam.users.api.exception.ResourceNotFoundException;
import com.alam.users.api.exception.ServiceUnavailableException;
import com.alam.users.api.logger.BaseLogger;
import com.alam.users.api.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController extends BaseLogger {
    private final UserService userService;
    int counter=0;
    @PostMapping("/saveUser")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
        UserDto userDto=userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
    @CircuitBreaker(name="ratingHotelBreaker",
            fallbackMethod = "getAllUsersFallback")
    @Retry(name="ratingHotelRetry")
    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUsers() {
        logger.info("GetAllUsers called");
        logger.info("Retry attempts : {}", counter);
        counter++;
        List<UserDto> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);

    }
    public ResponseEntity<?> getAllUsersFallback(Throwable throwable) {
        throwable.getStackTrace();
        if(throwable instanceof ResourceNotFoundException) {
            throw (ResourceNotFoundException) throwable;
        }
        throw new ServiceUnavailableException(
                "Hotel/Rating service is currently unavailable. Please try again later."
        );
    }
   /* @CircuitBreaker(name="ratingHotelBreaker",
            fallbackMethod = "getUserByIdFallback")
    @Retry(name="ratingHotelRetry")*/
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
        logger.info("Get user by id called with userId: {}", userId);
        logger.info("Retry attempt: {}", counter);
        counter++;
        UserDto userDto=userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }
    public ResponseEntity<?> getUserByIdFallback(String userId, Throwable throwable) {

        if(throwable instanceof ResourceNotFoundException) {
            throw (ResourceNotFoundException) throwable;
        } else if (throwable instanceof  ServiceUnavailableException) {
            throw new ServiceUnavailableException(
                    "Hotel/Rating service is currently unavailable. Please try again later."
            );
        }
        throw  new RuntimeException(throwable);
    }
    @GetMapping("/message")
    public String message(){
        return "Hello world..";
    }
}
