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
                FirebaseMessaging
                        .getInstance()
                        .subscribeToTopic(registrationTokens, topic);
            }
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) {
        String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
        log.info("Successfully sent message: " + response);
    }
}
