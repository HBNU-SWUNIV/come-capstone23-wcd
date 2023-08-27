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

    private Long clubId;

    private Long senderId;

    private String message;

    private LocalDateTime sendTime;

    @Builder
    public Chat(Long clubId, Long senderId, String message) {
        this.clubId = clubId;
        this.senderId = senderId;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }

}
