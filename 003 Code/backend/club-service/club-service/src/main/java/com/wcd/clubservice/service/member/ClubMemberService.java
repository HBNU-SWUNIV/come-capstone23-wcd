package com.wcd.clubservice.service.member;

import com.wcd.clubservice.dto.club.response.ResponseClubMemberIdsByClubId;
import com.wcd.clubservice.dto.clubMember.request.RequestJoinClubMember;
import com.wcd.clubservice.dto.clubMember.response.ResponseClubMember;
import com.wcd.clubservice.dto.clubMember.response.ResponseClubMembersByClubId;
import com.wcd.clubservice.enums.Grade;

import java.util.List;

public interface ClubMemberService {
    Long createClubMember(Long clubId, Long userId);

    List<ResponseClubMember> getClubMembers(Long clubId);

    void deleteClubMember(Long userId, Long clubId);

    Long setClubMemberGrade(Long clubId, Long userId, Grade grade);

    void deleteMember(Long userId);

    boolean isUserMemberOfClub(Long clubId, Long userId);

    ResponseClubMemberIdsByClubId getClubMemberIds(Long clubId);
}
