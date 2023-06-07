package com.wcd.chattingservice.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "채팅 정보 응답")
@Getter
@NoArgsConstructor
public class ResponseChat {
    @Schema(description = "채팅 번호")
    private Long id;

    @Schema(description = "채팅방 번호")
    private Long chatRoomId; // 방 번호

    @Schema(description = "채팅 송신자 멤버번호")
    private Long senderId;

    @Schema(description = "메세지")
    private String message;

    @Schema(description = "채팅 보낸 시간")
    private LocalDateTime sendTime;

    @QueryProjection
    public ResponseChat(Long id, Long chatRoomId, Long senderId, String message, LocalDateTime sendTime) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.message = message;
        this.sendTime = sendTime;
    }
}
