package com.wcd.fcmservice.fcmservice.service;

import com.google.firebase.messaging.*;
import com.wcd.fcmservice.fcmservice.dto.request.RequestChatMessage;
import com.wcd.fcmservice.fcmservice.dto.request.RequestSubscribe;
import com.wcd.fcmservice.fcmservice.dto.request.RequestUnsubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FCMService implements MessageService{

    @Override
    public void sendChatMessage(RequestChatMessage requestChatMessage) {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(requestChatMessage.getClubName())
                        .setBody(requestChatMessage.getUserName() + ": " + requestChatMessage.getChatMessage())
                        .build())
                .setTopic(requestChatMessage.getTopic())
                .build();

        log.info("sendChatMessage : {} ", message.toString());

        send(message);
    }

    public void subscribeTopic(RequestSubscribe requestSubscribe){
        List<String> registrationTokens = Arrays.asList(
                requestSubscribe.getToken()
        );

        log.info("token: {}", registrationTokens.get(0));
        log.info("request topic: {}", requestSubscribe.getTopicList().get(0));

        try {
            for(String topic : requestSubscribe.getTopicList()) {
                log.info("topic: {}", topic);
                TopicManagementResponse response = FirebaseMessaging
                        .getInstance()
                        .subscribeToTopic(registrationTokens, topic);

                log.info(response.getSuccessCount() + " tokens were subscribed successfully");
            }
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void unsubscribeTopic(RequestUnsubscribe requestUnsubscribe) {
        List<String> registrationTokens = Arrays.asList(
                requestUnsubscribe.getToken()
        );

        try {
            for(String topic : requestUnsubscribe.getTopicList()) {
                TopicManagementResponse response = FirebaseMessaging
                        .getInstance()
                        .unsubscribeFromTopic(registrationTokens, topic);

                log.info("{} tokens were unsubscribed successfully", response.getSuccessCount());

            }
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) {
        String response = null;
        try {
            response = String.valueOf(FirebaseMessaging.getInstance().send(message));
            log.info("Successfully sent message: {}", response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
