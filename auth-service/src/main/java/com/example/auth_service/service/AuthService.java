package com.example.auth_service.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.auth_service.client.UserClient;
import com.example.auth_service.dto.AuthResponse;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.ResponseDTO;
import com.example.auth_service.security.JwtProvider;
import com.example.auth_service.exception.UserCreationFailed;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProvider jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseDTO register(RegisterRequest request) throws UserCreationFailed {
        String encryptedPassword = passwordEncoder.encode(request.password());

        ResponseEntity<ResponseDTO> res = userClient.createUser(request.username(), encryptedPassword,
                request.email());
        if (res.getStatusCode().is2xxSuccessful()) {
            String token = jwt.generateToken(request.username());
            return new ResponseDTO(token, res.getStatusCode(), null, "User successfully created");

        }
        String errorMessage = (res.getBody() != null && res.getBody().message() != null)
                ? res.getBody().message()
                : "User creation failed";

        throw new UserCreationFailed(errorMessage);
    }

    /* public AuthResponse login(LoginRequest request) {
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

    } */

}
