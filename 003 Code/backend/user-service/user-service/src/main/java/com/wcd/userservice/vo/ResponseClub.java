package com.wcd.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.userservice.enums.ApprovalMethod;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
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

    private List<String> tags;
}

