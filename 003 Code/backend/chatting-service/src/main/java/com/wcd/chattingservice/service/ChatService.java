package com.wcd.chattingservice.service;

import com.wcd.chattingservice.client.UserServiceClient;
import com.wcd.chattingservice.dto.ChatDto;
import com.wcd.chattingservice.dto.RequestUsernames;
import com.wcd.chattingservice.dto.response.ResponseChat;

import com.wcd.chattingservice.dto.response.ResponseUsernames;
import com.wcd.chattingservice.dto.response.SendChat;
import com.wcd.chattingservice.entity.Chat;
import com.wcd.chattingservice.mapper.Mapper;
import com.wcd.chattingservice.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final Mapper mapper;
    private final UserServiceClient userServiceClient;

    @Transactional
    public SendChat saveMessage(ChatDto chat) {
        String sender = userServiceClient.getUsernameById(chat.getSenderId());
        return SendChat.builder().chat(chatRepository.save(chat.toEntity())).sender(sender).build();
    }

    public Page<ResponseChat> getChats(Long clubId, Pageable pageable) {
        Page<Chat> chatPage = chatRepository.findByClubIdOrderBySendTimeDesc(clubId, pageable);

        List<Long> senderIds = chatPage.getContent().stream()
                .map(Chat::getSenderId)
                .toList();

        RequestUsernames requestUsernames = new RequestUsernames(senderIds);
        ResponseUsernames responseUsernames = userServiceClient.getUsernamesByIds(requestUsernames);

        Map<Long, String> senderIdToUsernameMap = responseUsernames.getUserNames()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> Long.parseLong(entry.getKey()), Map.Entry::getValue));

        return chatPage.map(chat -> {
            String sender = senderIdToUsernameMap.get(chat.getSenderId());
            return ResponseChat.builder()
                    .id(chat.getId())
                    .clubId(chat.getClubId())
                    .senderId(chat.getSenderId())
                    .sender(sender)
                    .message(chat.getMessage())
                    .sendTime(chat.getSendTime())
                    .build();
        });
    }
}
