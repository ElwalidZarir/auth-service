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
        String encryptedPassword = passwordEncoder.encode(request.password());
        userClient.createUser(request.username(), encryptedPassword, request.email());
        String token = jwt.generateToken(request.username());

        return new AuthResponse(token);
    }
}
