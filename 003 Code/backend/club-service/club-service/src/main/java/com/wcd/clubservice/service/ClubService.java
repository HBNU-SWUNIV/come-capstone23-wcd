package com.wcd.clubservice.service;

import com.wcd.clubservice.dto.ClubDto;
import com.wcd.clubservice.dto.ClubMemberDto;
import com.wcd.clubservice.jpa.ClubEntity;
import com.wcd.clubservice.jpa.ClubMemberEntity;
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
}
