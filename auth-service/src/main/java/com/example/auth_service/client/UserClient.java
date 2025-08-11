package com.example.auth_service.client;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.dto.RegisterResponseDTO;

@Component
public class UserClient {
    private RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8082/users")
            .build();

    public ResponseEntity<RegisterResponseDTO> createUser(String username, String password, String email) {
        Map<String, String> req = Map.of(
                "email", email,
                "username", username,
                "password", password);

        try {
            return restClient.post()
                    .uri("/register")
                    .body(req)
                    .retrieve()
                    .toEntity(RegisterResponseDTO.class);
        } catch (RestClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(new RegisterResponseDTO(null, "user already exists"));

        }

    }

    /*
     * public LoginRequest getUser(String username) {
     * try {
     * return restClient.get()
     * .uri(uriBuilder -> uriBuilder
     * .path("/exists")
     * .queryParam("username", username)
     * .build())
     * .retrieve()
     * .body(LoginRequest.class);
     * } catch (Exception e) {
     * throw new RuntimeException("Error fetching user", e);
     * }
     * }
     */
    public boolean userExists(String username, String email) {
        try {
            var exists = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/exists")
                            .queryParam("username", username)
                            .queryParam("email", email)
                            .build())
                    .retrieve().body(Boolean.class);

            return exists != null && exists;
        } catch (Exception e) {
            throw new RuntimeException("Error checking user existences:" + e.getMessage());
        }
    }
}
