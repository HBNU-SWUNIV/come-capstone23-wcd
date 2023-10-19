package com.wcd.fcmservice.fcmservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestChatMessage {
    String clubName;
    String userName;
    String chatMessage;
    String topic;
}
