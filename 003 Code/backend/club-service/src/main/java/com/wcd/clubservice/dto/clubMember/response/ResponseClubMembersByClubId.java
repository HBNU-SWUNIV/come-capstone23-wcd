package com.wcd.clubservice.dto.clubMember.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.clubservice.dto.clubMember.ClubMemberDto;
import com.wcd.clubservice.entity.ClubMember;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClubMembersByClubId {

    List<ClubMemberDto> clubMemberList = new ArrayList<>();

    // EntityList => DTOList
    @Builder
    public ResponseClubMembersByClubId(List<ClubMember> clubMemberEntityList) {
        List<ClubMemberDto> clubMemberList = clubMemberEntityList.stream()
                .map(clubMember -> ClubMemberDto.builder()
                        .clubId(clubMember.getClub().getId())
                        .userId(clubMember.getUserId())
                        .isOnline(clubMember.isOnline())
                        .grade(clubMember.getGrade())
                        .isApproval(clubMember.isApproval())
                        .joinDate(clubMember.getJoinDate())
                        .build())
                .collect(Collectors.toList());

        this.clubMemberList = clubMemberList;
    }
}
