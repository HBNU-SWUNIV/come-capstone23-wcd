package com.wcd.clubservice.dto.club.request;

import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.Tag;
import com.wcd.clubservice.enums.ApprovalMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class RequestClub {

    private MultipartFile multipartFile;

    @NotNull(message = "host_id cannot be null")
    private Long hostId;

    @NotNull(message ="clubName cannot be null")
    private String clubName;

    @NotNull(message = "category cannot be null")
    private String category;

    private String description;

    @NotNull(message = "approvalMethod cannot be null")
    private ApprovalMethod approvalMethod;

    @NotNull(message = "maximumPeople cannot be null")
    private int maximumPeople;

    private List<String> tagList;

    public Club toEntity(String mainImageUrl) {
        Club clubBuild = Club.builder()
                .hostId(hostId)
                .clubName(clubName)
                .category(category)
                .description(description)
                .mainImageUrl(mainImageUrl)
                .approvalMethod(approvalMethod)
                .maximumPeople(maximumPeople)
                .recruitment(true)
                .createdAt(LocalDateTime.now())
                .build();

        this.tagList.stream()
                .map(tag -> Tag.builder().tag(tag).build())
                .forEach(clubBuild::addTag);

        return clubBuild;
    }
}
