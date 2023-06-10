package com.wcd.chattingservice.dto.request;

import com.wcd.chattingservice.entity.ChatRoom;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "채팅방 생성 요청")
@Getter
@NoArgsConstructor
public class RequestChatRoom {
    @Schema(description = "채팅방장 번호", example = "1")
    @NotBlank
    private Long masterId;

    @Schema(description = "채팅방 이름", example = "채팅방1")
    @NotBlank
    private String name;

    @Schema(description = "모임 번호", example = "1")
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
