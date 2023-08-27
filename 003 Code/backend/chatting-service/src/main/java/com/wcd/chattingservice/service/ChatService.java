package com.wcd.chattingservice.service;

import com.wcd.chattingservice.dto.ChatDto;
import com.wcd.chattingservice.dto.response.ResponseChat;

import com.wcd.chattingservice.entity.Chat;
import com.wcd.chattingservice.mapper.Mapper;
import com.wcd.chattingservice.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final Mapper mapper;

    @Transactional
    public ChatDto saveMessage(ChatDto chat) {
        return ChatDto.builder().chat(chatRepository.save(chat.toEntity())).build();
    }

    public Page<ResponseChat> getChats(Long clubId, Pageable pageable) {
        Page<Chat> chatPage = chatRepository.findByClubIdOrderBySendTimeDesc(clubId, pageable);
        return chatPage.map(mapper::mapToResponseChat);
    }
}
