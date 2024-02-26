package com.cutanddry.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String accessKeyId;
    private String secretKey;
    private String region;

}
