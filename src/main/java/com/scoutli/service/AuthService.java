package com.scoutli.service;

import com.scoutli.api.dto.AuthDTO;
import com.scoutli.domain.entity.User;
import com.scoutli.domain.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.HashSet;

@ApplicationScoped
@Slf4j
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public User register(AuthDTO.RegisterRequest request) {
        log.info("Attempting to register user: {}", request.email);
        if (userRepository.findByEmail(request.email) != null) {
            log.warn("Registration failed: Email {} already exists", request.email);
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.email);
        user.setPassword(request.password); // In real app: BCrypt.hashpw(request.password, BCrypt.gensalt())
        user.setRole("MEMBER");
        userRepository.persist(user);
        log.info("User registered successfully: {}", request.email);
        return user;
    }

    public String login(AuthDTO.LoginRequest request) {
        log.info("Attempting login for user: {}", request.email);
        User user = userRepository.findByEmail(request.email);
        if (user != null && user.getPassword().equals(request.password)) { // In real app: BCrypt.checkpw
            log.info("Login successful for user: {}", request.email);
            return generateToken(user.getEmail(), user.getRole());
        }
        log.warn("Login failed for user: {}", request.email);
        return null;
    }

    private String generateToken(String email, String role) {
        return Jwt.issuer("https://scoutli.com")
                .upn(email)
                .groups(new HashSet<>(Arrays.asList(role)))
                .expiresIn(3600)
                .sign();
    }
}
