package com.wcd.clubservice.dto.club.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.clubservice.entity.ClubMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "모임 회원 아이디 응답")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClubMemberIdsByClubId {

    private List<Long> userIdList = new ArrayList<>();

    @Builder
    public ResponseClubMemberIdsByClubId(List<ClubMember> clubMemberEntityList) {
        this.userIdList = clubMemberEntityList.stream()
                .map(ClubMember::getUserId)
                .collect(Collectors.toList());
    }
}
