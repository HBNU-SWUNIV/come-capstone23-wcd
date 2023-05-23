package com.wcd.chattingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private Long senderId;

    private String message;

    private LocalDateTime sendTime;

    @Builder
    public Chat(ChatRoom chatRoom, Long senderId, String message) {
        this.chatRoom =chatRoom;
        this.senderId = senderId;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }

}
