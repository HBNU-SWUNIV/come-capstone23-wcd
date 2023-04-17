package com.wcd.clubservice.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUpdateClub {

    @NotNull(message = "ClubName cannot be null")
    private String clubName;

    @NotNull(message = "Category cannot be null")
    private String category;

    private String description;

    private String mainImageUrl;

    @NotNull(message = "MaximumPeople cannot be null")
    private int maximumPeople;

    @NotNull(message = "Recruitment cannot be null")
    private boolean recruitment;
}
