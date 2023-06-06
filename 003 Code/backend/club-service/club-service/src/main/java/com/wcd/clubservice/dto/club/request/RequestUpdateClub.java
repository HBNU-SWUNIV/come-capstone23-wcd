package com.wcd.clubservice.dto.club.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class RequestUpdateClub {

    private MultipartFile multipartFile;

    @NotNull(message = "ClubName cannot be null")
    private String clubName;

    @NotNull(message = "Category cannot be null")
    private String category;

    private String description;

    @NotNull(message = "MaximumPeople cannot be null")
    private int maximumPeople;

    @NotNull(message = "Recruitment cannot be null")
    private boolean recruitment;
}
