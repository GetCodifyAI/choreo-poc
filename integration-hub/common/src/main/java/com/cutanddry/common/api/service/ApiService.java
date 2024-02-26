package com.cutanddry.common.api.service;

import com.cutanddry.common.api.model.AuthRequest;
import com.cutanddry.common.api.model.AuthResponse;
import com.cutanddry.common.api.model.CustomerData;
import com.cutanddry.common.api.properties.ApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service

public class ApiService {
    private final WebClient webClient;

    @Autowired
    public ApiService(WebClient webClient, ApiProperties apiProperties) {
        this.webClient = webClient;
    }

    public String authenticateAndGetToken(String email, String password, String timezone, String deviceId) {
        AuthRequest authRequest = new AuthRequest(email, password, timezone, deviceId);
        AuthResponse authResponse = webClient.post()
            .uri("/api/v1/auth")
            .bodyValue(authRequest)
            .retrieve()
            .bodyToMono(AuthResponse.class)
            .block();

        if (authResponse != null && authResponse.isAuthSuccess()) {
            return authResponse.getAccessToken();
        }

        throw new RuntimeException("Authentication failed");
    }

    public void createCustomer(CustomerData customerData, String accessToken) {
        webClient.post()
            .uri("/api/v1/customers")
            .header("access-token", accessToken)
            .bodyValue(customerData).retrieve()
            .toBodilessEntity()
            .block();
    }

    public boolean isTokenValid(String accessToken) {
        try {
            webClient.get()
                .uri("/api/v1/auth")
                .header("access-token", accessToken)
                .retrieve()
                .toBodilessEntity()
                .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
