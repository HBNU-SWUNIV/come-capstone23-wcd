package com.wcd.chattingservice.repository;

import com.wcd.chattingservice.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Page<Chat> findByClubIdOrderBySendTimeDesc(Long clubId, Pageable pageable);
}
