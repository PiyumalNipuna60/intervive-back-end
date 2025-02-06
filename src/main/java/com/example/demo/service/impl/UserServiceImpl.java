package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import com.example.demo.util.ModelMapperConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapperConfig modelMapper;

    public UserServiceImpl(UserRepo userRepo, ModelMapperConfig modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto registerUser(UserDto user) {
        User user1 = this.dtoToEntity(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        User save = this.userRepo.save(user1);
        return entityToDto(save);
    }

    @Override
    public UserDto userLogin(UserDto dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> userNames = userRepo.findByUserName(dto.getUserName());
        for (User name : userNames) {
            boolean isPasswordMatches = passwordEncoder.matches(dto.getPassword(), name.getPassword());
            if (isPasswordMatches) {
                return entityToDto(name);
            }
        }
        return null;
    }


    private User dtoToEntity(UserDto dto) {
        return modelMapper.modelMapper().map(dto, User.class);
    }

    private UserDto entityToDto(User user) {
        return (user == null) ? null : modelMapper.modelMapper().map(user, UserDto.class);
    }
}
