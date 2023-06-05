package com.wcd.chattingservice.service;

import com.wcd.chattingservice.dto.ChatRoomDto;
import com.wcd.chattingservice.dto.request.RequestChatRoom;
import com.wcd.chattingservice.dto.response.ResponseChatRoom;
import com.wcd.chattingservice.entity.ChatRoom;
import com.wcd.chattingservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long createChatRoom(RequestChatRoom requestChatRoom) {
        ChatRoom result = chatRoomRepository.save(requestChatRoom.toEntity());
        return result.getId();
    }

    public ResponseChatRoom getChatRoom(Long clubId) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findByClubId(clubId);

        return ResponseChatRoom.builder().chatRoomList(chatRoomList).build();
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

    @Transactional
    public void deleteChatRoomByClubId(Long clubId) {
        if(chatRoomRepository.existsByClubId(clubId)) {
            chatRoomRepository.deleteByClubId(clubId);
        }
    }
}
