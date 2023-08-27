package com.wcd.chattingservice.dto;

import com.wcd.chattingservice.entity.Chat;
import lombok.Builder;
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

    @Builder
    public ChatDto(Chat chat) {
        this.clubId = chat.getClubId();
        this.senderId = chat.getSenderId();
        this.message = chat.getMessage();
        this.sendTime = chat.getSendTime();
    }

    public Chat toEntity() {
        return Chat.builder()
                .clubId(clubId)
                .senderId(senderId)
                .message(message)
                .build();
    }
}