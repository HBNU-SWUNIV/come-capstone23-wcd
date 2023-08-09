package com.wcd.chattingservice.controller;

import com.wcd.chattingservice.dto.ChatDto;
import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "채팅 API")
@RestController
@RequiredArgsConstructor
public class ChatController {
    // 메시지 브로커와 상호작용하여 WebSocket 메시지를 전송하는 데 사용
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Operation(summary = "채팅", description = "채팅 브로드캐스트")
    // /chat/send 엔드포인트로 들어오는 WebSocket 메시지를 처리한다.
    @MessageMapping("/chat/send")
    public void sendMessage(@Payload ChatDto chat) {
        // 채팅 저장
        ChatDto result = chatService.saveMessage(chat);
        // 해당 채팅 메시지를 WebSocket 토픽(/topic/채팅방ID)에 전송하여 클라이언트에게 브로드캐스팅한다.
        messagingTemplate.convertAndSend("/topic/" + chat.getClubId(), result);
    }

    @Operation(summary = "채팅 조회", description = "채팅 조회 (페이지네이션)")
    @Parameters({
            @Parameter(name = "clubId", description = "모임 아이디", example = "1"),
            @Parameter(name = "page", description = "페이지", example = "1"),
            @Parameter(name = "size", description = "크기", example = "10"),
    })

    @GetMapping("/chat")
    public ResponseEntity<Page<ResponseChat>> getChatsByClubId(@RequestParam Long clubId, Pageable pageable) {
        Page<ResponseChat> responseChats = chatService.getChats(clubId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responseChats);
    }
}
