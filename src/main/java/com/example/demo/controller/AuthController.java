package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.util.JWTTokenGenerator;
import com.example.demo.util.TokenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class AuthController {
    private final UserService userService;
    private final JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    public AuthController(UserService userService, JWTTokenGenerator jwtTokenGenerator) {
        this.userService = userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserDto userDto, @RequestHeader(name = "Authorization") String authorizationHeader) {
            UserDto dto = this.userService.registerUser(userDto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> postLogin(@RequestBody UserDto dto) {
        UserDto user = userService.userLogin(dto);

        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Wrong details");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            String token = jwtTokenGenerator.generateJwtToken(user);
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
    }
}
