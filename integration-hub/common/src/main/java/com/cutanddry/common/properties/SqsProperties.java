package com.cutanddry.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsProperties {

    private String queueUrl;
    private String messageGroupId;
    private String messageDeduplicationId;
    private String MessageBody;
    private int maxMessages;
}
