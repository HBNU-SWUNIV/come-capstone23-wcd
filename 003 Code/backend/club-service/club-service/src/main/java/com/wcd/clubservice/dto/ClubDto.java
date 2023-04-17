package com.wcd.clubservice.dto;

import com.wcd.clubservice.enums.ApprovalMethod;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    private List<String> tags;
}
