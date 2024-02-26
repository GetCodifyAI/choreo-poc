package com.cutanddry.common.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
    private String timezone;
    @JsonProperty("device_id")
    private String deviceId;
}
