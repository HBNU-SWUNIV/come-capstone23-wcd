package com.wcd.chattingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public ChatRoom(Long masterId, String name, Long clubId) {
        this.masterId = masterId;
        this.name = name;
        this.clubId = clubId;
    }
}
