package com.alam.users.api.service;

import com.alam.users.api.dto.UserDto;
import com.alam.users.api.entities.User;

import java.util.List;

public interface UserService {
    UserDto save(UserDto user);
    List<UserDto> getAllUser();
    UserDto getUserById(String userId);


}
