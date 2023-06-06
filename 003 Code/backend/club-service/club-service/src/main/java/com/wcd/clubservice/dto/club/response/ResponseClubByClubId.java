package com.wcd.clubservice.dto.club.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.enums.ApprovalMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClubByClubId {
    private Long id;
    private Long hostId;
    private String clubName;
    private String category;
    private String description;
    private String mainImageUrl;
    private ApprovalMethod approvalMethod;
    private int maximumPeople;
    private boolean recruitment;
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
