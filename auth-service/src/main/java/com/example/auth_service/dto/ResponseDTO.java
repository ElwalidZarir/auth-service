package com.example.auth_service.dto;

import org.springframework.http.HttpStatusCode;

public record ResponseDTO(
        HttpStatusCode status,
        String error,
        String message) {
}