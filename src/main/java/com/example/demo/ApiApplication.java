package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);
    private final UserRepo userRepo;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public ApiApplication(UserRepo repository) {
        this.userRepo = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @PostConstruct
    public void initUsers() {
        try {
            String encodePassword = passwordEncoder.encode("1234");
            userRepo.save(new User(1L, "piyumal","0782862763", "piyumal", encodePassword));

        } catch (Exception e) {
            logger.error("An error occurred during user initialization.", e);
        }

    }
}
