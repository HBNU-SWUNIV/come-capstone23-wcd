package com.wcd.clubservice.dto.clubMember.response;

import com.wcd.clubservice.enums.Grade;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseClubMember {
    private Long clubId;
    private Long userId;
    private String userName;
    private boolean isOnline;
    private Grade grade;
    private boolean isApproval;
    private LocalDateTime joinDate;

    @Builder
    public ResponseClubMember(Long clubId, Long userId, String userName, boolean isOnline, Grade grade, boolean isApproval, LocalDateTime joinDate) {
        this.clubId = clubId;
        this.userId = userId;
        this.userName = userName;
        this.isOnline = isOnline;
        this.grade = grade;
        this.isApproval = isApproval;
        this.joinDate = joinDate;
    }
}
