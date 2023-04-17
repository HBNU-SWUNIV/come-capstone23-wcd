package com.wcd.clubservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMemberEntity, Long> {
    Iterable<ClubMemberEntity> findByClubId(Long clubId);
    void deleteByClubIdAndUserId(Long clubId, Long userId);
}
