package com.wcd.clubservice.dto.club.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import com.wcd.clubservice.enums.ApprovalMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseClub {
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

}
