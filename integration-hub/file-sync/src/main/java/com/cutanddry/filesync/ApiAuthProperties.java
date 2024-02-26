package com.cutanddry.filesync;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class ApiAuthProperties {

    private String baseUrl;
    private String authEmail;
    private String authPassword;
    private String authTimezone;
    private String authDeviceId;
}
