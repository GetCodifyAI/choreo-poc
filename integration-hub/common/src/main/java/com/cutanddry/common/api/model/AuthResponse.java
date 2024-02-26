package com.cutanddry.common.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("auth_success")
    private boolean authSuccess;
    private long expiration;
    private User user;

    @Data
    public static class User {
        private long id;
        private String name;
        private String locale;
    }
}
