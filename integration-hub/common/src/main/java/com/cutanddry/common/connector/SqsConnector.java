package com.cutanddry.common.connector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class SqsConnector {

    private final SqsClient sqsClient;

    private static final Logger logger = LoggerFactory.getLogger(SqsConnector.class);

    public void sendMessage(String queueUrl, String messageBody, String messageGroupId, String messageDeduplicationId) {
        SendMessageResponse response = sqsClient.sendMessage(
            SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .messageGroupId(messageGroupId)
                .messageDeduplicationId(messageDeduplicationId).build()
        );

        logger.info("Message sent. MessageId: {}", response.messageId());
    }

    public List<Message> receiveMessages(String queueUrl, int maxMessages) {
        ReceiveMessageResponse response = sqsClient.receiveMessage(
            ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(maxMessages)
                .build()
        );

        List<Message> messages = response.messages();
        for (Message message : messages) {
            logger.info("Received message: {}", message.body());
        }

        return messages;
    }

    public void deleteMessage(String queueUrl, Message message) {
        sqsClient.deleteMessage(deleteMessageRequest(queueUrl, message));

        logger.info("Message deleted. ReceiptHandle: {}", message.receiptHandle());
    }

    private DeleteMessageRequest deleteMessageRequest(String queueUrl, Message message) {
        return DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build();
    }
}
