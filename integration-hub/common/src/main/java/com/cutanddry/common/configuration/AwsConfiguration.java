package com.cutanddry.common.configuration;

import com.cutanddry.common.properties.AwsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfiguration {

    private final AwsProperties awsProperties;
    private static final Logger logger = LoggerFactory.getLogger(AwsConfiguration.class);

    public AwsConfiguration(AwsProperties awsProperties) {
        assert awsProperties != null : "awsProperties must not be null";
        this.awsProperties = awsProperties;
    }

    @Bean
    public SqsClient sqsClient() {
        try {
            AwsCredentialsProvider credentialsProvider =
                    StaticCredentialsProvider.create(AwsBasicCredentials.create(awsProperties.getAccessKeyId(), awsProperties.getSecretKey()));

            SqsClient sqsClient = SqsClient.builder()
                    .region(Region.of(awsProperties.getRegion()))
                    .credentialsProvider(credentialsProvider)
                    .build();

            logger.info("SqsClient successfully created");

            return sqsClient;
        } catch (SdkClientException e) {
            logger.error("Failed to create SqsClient. Error: {}", e.getMessage(), e);
            throw e;
        }
    }
}
