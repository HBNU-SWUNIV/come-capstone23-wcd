package com.wcd.chattingservice.dto.response;

import com.wcd.chattingservice.dto.ChatRoomDto;
import com.wcd.chattingservice.entity.ChatRoom;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "채팅방 정보 응답")
@Getter
@NoArgsConstructor
public class ResponseChatRoom {

    @Schema(description = "채팅방 정보 리스트 응답")
    List<ChatRoomDto> chatRoomDtoList;

    @Builder
    public ResponseChatRoom(List<ChatRoom> chatRoomList) {
        List<ChatRoomDto> chatRoomDtoList = chatRoomList.stream()
                .map(chatRoom -> ChatRoomDto.builder()
                        .chatRoomId(chatRoom.getId())
                        .masterId(chatRoom.getMasterId())
                        .name(chatRoom.getName())
                        .clubId(chatRoom.getClubId())
                        .build())
                .collect(Collectors.toList());

        this.chatRoomDtoList = chatRoomDtoList;
    }
}