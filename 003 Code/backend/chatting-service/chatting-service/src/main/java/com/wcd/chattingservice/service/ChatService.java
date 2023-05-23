package com.wcd.chattingservice.service;

import com.wcd.chattingservice.dto.ChatDto;
import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.entity.ChatRoom;
import com.wcd.chattingservice.repository.ChatRepository;
import com.wcd.chattingservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void saveMessage(ChatDto chat) {
        ChatRoom chatRoom = chatRoomRepository.findById(chat.getChatRoomId())
                .orElseThrow(() -> new NoSuchElementException("ChatRoom not found with id: " + chat.getChatRoomId()));

        chatRepository.save(chat.toEntity(chatRoom));
    }

    public Page<ResponseChat> getChats(Pageable pageable) {
        return chatRepository.getChats(pageable);
    }
}
