package com.wcd.clubservice.dto.club.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import com.wcd.clubservice.enums.ApprovalMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "모임 정보 응답")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClub {
    @Schema(description = "모임 번호")
    private Long id;

    @Schema(description = "모임장 유저번호")
    private Long hostId;

    private String hostName;

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

    @QueryProjection
    public ResponseClub(Long id, Long hostId, String clubName, String category, String description, String mainImageUrl, ApprovalMethod approvalMethod, int maximumPeople, boolean recruitment, LocalDateTime createdAt) {
        this.id = id;
        this.hostId = hostId;
        this.clubName = clubName;
        this.category = category;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.approvalMethod = approvalMethod;
        this.maximumPeople = maximumPeople;
        this.recruitment = recruitment;
        this.createdAt = createdAt;
    }

    @Builder
    public ResponseClub(Long id, Long hostId, String hostName, String clubName, String category, String description, String mainImageUrl, ApprovalMethod approvalMethod, int maximumPeople, boolean recruitment, LocalDateTime createdAt) {
        this.id = id;
        this.hostId = hostId;
        this.hostName = hostName;
        this.clubName = clubName;
        this.category = category;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.approvalMethod = approvalMethod;
        this.maximumPeople = maximumPeople;
        this.recruitment = recruitment;
        this.createdAt = createdAt;
    }
}
