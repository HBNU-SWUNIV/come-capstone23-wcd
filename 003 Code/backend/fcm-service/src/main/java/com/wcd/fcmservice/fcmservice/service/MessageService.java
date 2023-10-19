package com.wcd.fcmservice.fcmservice.service;


import com.wcd.fcmservice.fcmservice.dto.request.RequestChatMessage;

public interface MessageService {

    void sendChatMessage(RequestChatMessage requestChatMessage);
}
