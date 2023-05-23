package com.wcd.chattingservice.dto.request;

import com.wcd.chattingservice.entity.ChatRoom;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestChatRoom {
    @NotBlank
    private Long masterId;

    @NotBlank
    private String name;

    @NotBlank
    private Long clubId;

    @Builder
    public RequestChatRoom(Long masterId, String name, Long clubId) {
        this.masterId = masterId;
        this.name = name;
        this.clubId = clubId;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .masterId(masterId)
                .name(name)
                .clubId(clubId)
                .build();
    }
}
