package com.wcd.clubservice.dto.club.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.enums.ApprovalMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "모임 정보 응답 (club-id)")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClubByClubId {
    @Schema(description = "모임 번호")
    private Long id;

    @Schema(description = "모임장 유저번호")
    private Long hostId;

    @Schema(description = "모임 이름")
    private String clubName;

    @Schema(description = "카테고리")
    private String category;

    @Schema(description = "모임 설명")
    private String description;

    @Schema(description = "모임 메인 이미지")
    private String mainImageUrl;

    @Schema(description = "승인 방법")
    private ApprovalMethod approvalMethod;

    @Schema(description = "최대 수용 인원")
    private int maximumPeople;

    @Schema(description = "모집 여부")
    private boolean recruitment;

    @Schema(description = "생성 날짜")
    private LocalDateTime createdAt;

    @Builder
    public ResponseClubByClubId(Club club) {
        this.id = club.getId();
        this.hostId = club.getHostId();
        this.clubName = club.getClubName();
        this.category = club.getCategory();
        this.description = club.getDescription();
        this.mainImageUrl = club.getMainImageUrl();
        this.approvalMethod = club.getApprovalMethod();
        this.maximumPeople = club.getMaximumPeople();
        this.recruitment = club.isRecruitment();
        this.createdAt = club.getCreatedAt();
    }
}
