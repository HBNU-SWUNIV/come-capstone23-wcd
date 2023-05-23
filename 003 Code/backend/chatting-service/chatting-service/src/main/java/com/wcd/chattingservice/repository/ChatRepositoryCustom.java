package com.wcd.chattingservice.repository;

import com.wcd.chattingservice.dto.response.ResponseChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRepositoryCustom {
    Page<ResponseChat> getChats(Pageable pageable);
}
