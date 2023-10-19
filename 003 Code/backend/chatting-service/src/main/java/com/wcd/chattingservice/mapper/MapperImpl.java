package com.wcd.chattingservice.mapper;

import com.wcd.chattingservice.client.UserServiceClient;
import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperImpl implements Mapper{
    private final UserServiceClient userServiceClient;
    @Override
    public ResponseChat mapToResponseChat(Chat chat) {
        return ResponseChat.builder()
                .id(chat.getId())
                .clubId(chat.getClubId())
                .senderId(chat.getSenderId())
                .message(chat.getMessage())
                .sendTime(chat.getSendTime())
                .build();
    }
}
