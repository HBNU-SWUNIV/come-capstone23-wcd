package com.wcd.chattingservice.repository;

import com.wcd.chattingservice.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByClubId(Long clubId);

    boolean existsByClubId(Long clubId);

    @Modifying
    @Query("DELETE FROM ChatRoom cr WHERE cr.clubId = :clubId")
    void deleteByClubId(Long clubId);
}
