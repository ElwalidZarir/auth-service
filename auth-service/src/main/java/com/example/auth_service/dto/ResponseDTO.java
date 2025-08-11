package com.example.auth_service.dto;

import org.springframework.http.HttpStatusCode;

public record ResponseDTO(
        String token,
        HttpStatusCode status,
        String error,
        String message) {
}