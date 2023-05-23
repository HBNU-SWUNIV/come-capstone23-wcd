package com.wcd.chattingservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long masterId;

    private String name;

    private Long clubId;

    @Builder
    public ChatRoom(Long masterId, String name, Long clubId) {
        this.masterId = masterId;
        this.name = name;
        this.clubId = clubId;
    }
}
