package com.example.demo.service;

import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto user);
    UserDto userLogin(UserDto dto);
}
