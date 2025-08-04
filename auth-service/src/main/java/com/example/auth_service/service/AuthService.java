package com.example.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth_service.client.UserClient;
import com.example.auth_service.dto.AuthResponse;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.security.JwtProvider;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProvider jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        try {
            if (userClient.userExists(request.username(), request.email())) {
                return new AuthResponse(null, "User already exists");
            }
            String encryptedPassword = passwordEncoder.encode(request.password());
            userClient.createUser(request.username(), encryptedPassword, request.email());
            String token = jwt.generateToken(request.username());
            return new AuthResponse(token, null);
        } catch (Exception e) {
            return new AuthResponse(null, e.getMessage());
        }
    }

    public AuthResponse login(LoginRequest request) {
        try {
            LoginRequest user = userClient.getUser(request.username()); 
            if (!passwordEncoder.matches(request.password(), user.password())) {
                return new AuthResponse(null, "Invalid username or password");
            }
            String token = jwt.generateToken(request.username());
            return new AuthResponse(token, null);
        } catch (Exception e) {
            return new AuthResponse(null, e.getMessage());
        }

    }
}
