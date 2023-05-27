package com.wcd.clubservice.dto.clubMember;

import com.wcd.clubservice.enums.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClubMemberDto {
    private Long clubId;
    private Long userId;
    private boolean isOnline;
    private Grade grade;
    private boolean isApproval;
    private LocalDateTime joinDate;

    @Builder
    public ClubMemberDto(Long clubId, Long userId, boolean isOnline, Grade grade, boolean isApproval, LocalDateTime joinDate) {
        this.clubId = clubId;
        this.userId = userId;
        this.isOnline = isOnline;
        this.grade = grade;
        this.isApproval = isApproval;
        this.joinDate = joinDate;
    }
}
