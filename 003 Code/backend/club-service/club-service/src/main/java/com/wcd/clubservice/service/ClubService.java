package com.wcd.clubservice.service;

import com.wcd.clubservice.dto.ClubDto;
import com.wcd.clubservice.dto.ClubMemberDto;
import com.wcd.clubservice.entity.ClubEntity;
import com.wcd.clubservice.entity.ClubMemberEntity;
import com.wcd.clubservice.vo.RequestUpdateClub;

public interface ClubService {
    Iterable<ClubEntity> getClub();

    ClubDto createClub(ClubDto clubDto);

    ClubDto getClubById(Long clubId);

    ClubDto updateClubById(Long clubId, RequestUpdateClub requestUpdateClub);

    void deleteClub(Long clubId);

    Iterable<ClubMemberEntity> getClubMember(Long clubId);

    ClubMemberDto createClubMember(Long clubId, Long userId);

    void deleteClubMember(Long clubId, Long userId);

    Iterable<ClubEntity> getClubByUserId(Long userId);
}
