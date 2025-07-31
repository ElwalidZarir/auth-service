package com.example.auth_service.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.example.auth_service.dto.RegisterRequest;

@Component
public class UserClient {
    private String baseUrl = "http://localhost:8082/users";
    private RestTemplate restClient = new RestTemplate();

    public void createUser(String username, String password, String email) {
        Map<String, String> req = Map.of(
                "email", email,
                "username", username,
                "password", password);
        restClient.postForEntity(baseUrl + "/register", req, Void.class);
    }

}
