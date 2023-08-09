package com.wcd.chattingservice.dto;

import com.wcd.chattingservice.entity.Chat;
import com.wcd.chattingservice.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 채팅 내용을 위한 DTO
@Getter
@NoArgsConstructor
public class ChatDto {

    private Long clubId; // 클럽 번호
    private Long senderId;
    private String message;
    private LocalDateTime sendTime;

    public Chat toEntity(ChatRoom chatRoom) {
        return Chat.builder()
                .chatRoom(chatRoom)
                .senderId(senderId)
                .message(message)
                .build();
    }
}