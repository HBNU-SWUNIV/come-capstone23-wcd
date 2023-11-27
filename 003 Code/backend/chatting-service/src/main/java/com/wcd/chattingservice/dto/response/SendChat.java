package com.wcd.chattingservice.dto.response;

import com.wcd.chattingservice.entity.Chat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendChat {

    private Long clubId; // 클럽 번호
    private Long senderId;
    private String sender;
    private String message;
    private LocalDateTime sendTime;

    @Builder
    public SendChat(Chat chat, String sender) {
        this.clubId = chat.getClubId();
        this.senderId = chat.getSenderId();
        this.sender = sender;
        this.message = chat.getMessage();
        this.sendTime = chat.getSendTime();
    }
}