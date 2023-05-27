package com.wcd.clubservice.repository.member;

import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    // 모임 멤버 상세 조회 (club-id)
    List<ClubMember> findByClubId(Long clubId);

    Optional<ClubMember> findByUserIdAndClubId(Long userId, Long clubId);

    // 모임 상세 조회 (user-id)
    @Query("SELECT cm.club FROM ClubMember cm WHERE cm.userId = :userId")
    List<Club> findClubByUserId(@Param("userId") Long userId);
}
