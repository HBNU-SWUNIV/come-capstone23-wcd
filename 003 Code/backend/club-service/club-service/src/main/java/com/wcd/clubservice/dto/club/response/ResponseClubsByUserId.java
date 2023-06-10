package com.wcd.clubservice.dto.club.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.clubservice.dto.club.ClubDto;
import com.wcd.clubservice.entity.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "모임 정보 응답 (user-id)")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClubsByUserId {
    @Schema(description = "모임 정보 리스트")
    List<ClubDto> clubList;

    @Builder
    public ResponseClubsByUserId(List<Club> clubEntityList) {
        List<ClubDto> clubList = clubEntityList.stream()
                .map(club -> ClubDto.builder()
                        .id(club.getId())
                        .hostId(club.getHostId())
                        .clubName(club.getClubName())
                        .category(club.getCategory())
                        .description(club.getDescription())
                        .mainImageUrl(club.getMainImageUrl())
                        .approvalMethod(club.getApprovalMethod())
                        .maximumPeople(club.getMaximumPeople())
                        .recruitment(club.isRecruitment())
                        .createdAt(club.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        this.clubList = clubList;
    }
}
