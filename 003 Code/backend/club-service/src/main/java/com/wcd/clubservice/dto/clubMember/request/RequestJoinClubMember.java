package com.wcd.clubservice.dto.clubMember.request;

import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.ClubMember;
import com.wcd.clubservice.enums.ApprovalMethod;
import com.wcd.clubservice.enums.Grade;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RequestJoinClubMember {
    @NotNull
    private Long userId;
    @NotNull
    private Long clubId;

    // 모임 가입
    public ClubMember joinClubMember(Club club) {
        Boolean isApproval;

        if(club.getApprovalMethod().equals(ApprovalMethod.FREE)) {
            isApproval = true;
        } else {
            isApproval = false;
        }

        return ClubMember.builder()
                .club(club)
                .userId(userId)
                .grade(Grade.BRONZE)
                .joinDate(LocalDateTime.now())
                .isApproval(isApproval)
                .build();
    }
}
