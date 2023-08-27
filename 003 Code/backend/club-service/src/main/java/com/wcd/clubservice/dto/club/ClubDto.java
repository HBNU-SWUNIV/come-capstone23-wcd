package com.wcd.clubservice.dto.club;

import com.wcd.clubservice.enums.ApprovalMethod;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClubDto {
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
    public ClubDto(Long id, Long hostId, String clubName, String category, String description, String mainImageUrl, ApprovalMethod approvalMethod, int maximumPeople, boolean recruitment, LocalDateTime createdAt) {
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
