package com.alam.users.api.controller;

import com.alam.users.api.dto.UserDto;
import com.alam.users.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/saveUser")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
        UserDto userDto=userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);

    }
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") String userId) {
        UserDto userDto=userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {

        return null;
    }
    public ResponseEntity<UserDto> deleteUser(@RequestBody UserDto user) {
        return null;
    }
}
