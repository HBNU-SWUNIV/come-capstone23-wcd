package com.wcd.chattingservice.mapper;

import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.entity.Chat;

public interface Mapper {

    ResponseChat mapToResponseChat(Chat chat);
}
