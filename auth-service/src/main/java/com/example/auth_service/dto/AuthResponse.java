package com.example.auth_service.dto;

import org.springframework.http.HttpStatusCode;

public record AuthResponse(HttpStatusCode status, String token, String error) {
}
