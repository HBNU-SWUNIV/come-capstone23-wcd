package com.wcd.clubservice.dto;

import com.wcd.clubservice.enums.Grade;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClubMemberDto {
    private Long clubId;
    private Long userId;
    private boolean isOnline;
    private Grade grade;
    private boolean isApproval;
    private LocalDateTime joinDate;
}
