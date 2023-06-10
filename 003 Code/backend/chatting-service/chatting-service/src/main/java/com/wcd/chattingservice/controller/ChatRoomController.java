package com.wcd.chattingservice.controller;

import com.wcd.chattingservice.service.ChatRoomService;
import com.wcd.chattingservice.dto.request.RequestChatRoom;
import com.wcd.chattingservice.dto.response.ResponseChatRoom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채팅방 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final Environment env;

    @Operation(summary = "채팅방 생성", description = "채팅방 생성")
    @PostMapping("/room")
    public ResponseEntity<Long> createChatRoom(@RequestBody RequestChatRoom requestChatRoom) {
        Long chatRoomId = chatRoomService.createChatRoom(requestChatRoom);

        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomId);
    }

    @Operation(summary = "채팅방 조회(club-id)", description = "club-id에 해당하는 채팅방 조회")
    @GetMapping("/room/{club_id}")
    public ResponseEntity<ResponseChatRoom> getChatRoom(@PathVariable("club_id") Long clubId) {
        ResponseChatRoom responseChatRoom = chatRoomService.getChatRoom(clubId);

        return ResponseEntity.status(HttpStatus.OK).body(responseChatRoom);
    }


    @Operation(summary = "채팅방 삭제(chat_room_id)", description = "chat_room_id에 해당하는 채팅방 삭제")
    @DeleteMapping("/room/{chat_room_id}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable("chat_room_id") Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "채팅방 삭제(club_id)", description = "club_id에 해당하는 채팅방 삭제")
    @DeleteMapping("/room/{club_id}")
    public ResponseEntity<Void> deleteChatRoomByClubId(@PathVariable("club_id") Long clubId) {
        chatRoomService.deleteChatRoomByClubId(clubId);

        return ResponseEntity.noContent().build();
    }
}
