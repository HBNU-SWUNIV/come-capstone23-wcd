package com.wcd.clubservice.dto.club.request;

import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.Tag;
import com.wcd.clubservice.enums.ApprovalMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
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

    private List<String> tagList;

    public Club toEntity() {
        Club clubBuild = Club.builder()
                .hostId(hostId)
                .clubName(clubName)
                .category(category)
                .description(description)
                .mainImageUrl(mainImageUrl)
                .approvalMethod(approvalMethod)
                .maximumPeople(maximumPeople)
                .build();

        this.tagList.stream()
                .map(tag -> Tag.builder().tag(tag).build())
                .forEach(clubBuild::addTag);

        return clubBuild;
    }
}
