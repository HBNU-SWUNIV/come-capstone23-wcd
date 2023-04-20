package com.wcd.clubservice.repository;

import com.wcd.clubservice.entity.ClubEntity;
import com.wcd.clubservice.entity.ClubMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMemberEntity, Long> {
    // 모임 멤버 상세 조회 (club-id)
    Iterable<ClubMemberEntity> findByClubId(Long clubId);

    // 모임 멤버 삭제
    void deleteByClubIdAndUserId(Long clubId, Long userId);

    // 모임 상세 조회 (user-id)
    @Query("SELECT cm.club FROM ClubMemberEntity cm WHERE cm.userId = :userId")
    List<ClubEntity> findClubByUserId(@Param("userId") Long userId);
}
