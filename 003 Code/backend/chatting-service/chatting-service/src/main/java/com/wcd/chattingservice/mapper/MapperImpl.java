package com.wcd.chattingservice.mapper;

import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.entity.Chat;
import org.springframework.stereotype.Component;

@Component
public class MapperImpl implements Mapper{
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
