package com.wcd.clubservice.service.member;

import com.wcd.clubservice.dto.club.response.ResponseClubMemberIdsByClubId;
import com.wcd.clubservice.dto.clubMember.request.RequestJoinClubMember;
import com.wcd.clubservice.dto.clubMember.response.ResponseClubMembersByClubId;

public interface ClubMemberService {
    Long createClubMember(RequestJoinClubMember requestJoinClubMember);

    ResponseClubMembersByClubId getClubMembers(Long clubId);

    void deleteClubMember(Long userId, Long clubId);

    void deleteMember(Long userId);

    boolean isUserMemberOfClub(Long clubId, Long userId);

    ResponseClubMemberIdsByClubId getClubMemberIds(Long clubId);
}
