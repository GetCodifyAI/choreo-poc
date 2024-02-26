package com.cutanddry.common.api.controller;

import com.cutanddry.common.api.model.CustomerData;
import com.cutanddry.common.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String email, @RequestParam String password, @RequestParam String timezone, @RequestParam String deviceId) {
        return apiService.authenticateAndGetToken(email, password, timezone, deviceId);
    }

    @PostMapping("/create-customer")
    public void createCustomer(@RequestBody CustomerData customerData, @RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = extractAccessToken(authorizationHeader);
        apiService.createCustomer(customerData, accessToken);
    }

    @GetMapping("/is-token-valid")
    public boolean isTokenValid(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = extractAccessToken(authorizationHeader);
        return apiService.isTokenValid(accessToken);
    }

    private String extractAccessToken(String authorizationHeader) {
        // Extract the access token from the Authorization header (e.g., "Bearer <access_token>")
        return authorizationHeader.replace("Bearer ", "");
    }
}
