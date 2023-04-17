package com.wcd.clubservice.vo;

import com.wcd.clubservice.enums.ApprovalMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RequestClub {

    @NotNull(message = "host_id cannot be null")
    private Long hostId;

    @NotNull(message ="clubName cannot be null")
    private String clubName;

    @NotNull(message = "category cannot be null")
    private String category;

    private String description;

    private String mainImageUrl;

    @NotNull(message = "approvalMethod cannot be null")
    private ApprovalMethod approvalMethod;

    @NotNull(message = "maximumPeople cannot be null")
    private int maximumPeople;

    private List<String> tags;
}
