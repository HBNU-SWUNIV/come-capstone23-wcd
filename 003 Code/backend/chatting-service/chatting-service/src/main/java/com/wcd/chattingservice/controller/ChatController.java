package com.wcd.chattingservice.controller;

import com.wcd.chattingservice.dto.ChatDto;
import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    // 메시지 브로커와 상호작용하여 WebSocket 메시지를 전송하는 데 사용
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    // /chat/send 엔드포인트로 들어오는 WebSocket 메시지를 처리한다.
    @MessageMapping("/chat/send")
    public void sendMessage(@Payload ChatDto chat) {
        // 채팅 저장
        chatService.saveMessage(chat);
        // 해당 채팅 메시지를 WebSocket 토픽(/topic/채팅방ID)에 전송하여 클라이언트에게 브로드캐스팅한다.
        messagingTemplate.convertAndSend("/topic/" + chat.getChatRoomId(), chat);
    }

    @GetMapping("/chat")
    public ResponseEntity<Page<ResponseChat>> getChats(Pageable pageable) {
        Page<ResponseChat> chatPage = chatService.getChats(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(chatPage);
    }
}
