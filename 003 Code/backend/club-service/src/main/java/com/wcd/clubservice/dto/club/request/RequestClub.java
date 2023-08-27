package com.wcd.clubservice.dto.club.request;

import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.Tag;
import com.wcd.clubservice.enums.ApprovalMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "모임 생성 요청")
@Getter
@AllArgsConstructor
public class RequestClub {

    @Schema(description = "모임 메인 사진")
    private MultipartFile multipartFile;

//    @Schema(description = "모임장 유저번호", example = "1")
//    @NotNull(message = "host_id cannot be null")
//    private Long hostId;

    @Schema(description = "모임 이름", example = "WCD 모임")
    @NotNull(message ="clubName cannot be null")
    private String clubName;

    @Schema(description = "카테고리", example = "독서")
    @NotNull(message = "category cannot be null")
    private String category;

    @Schema(description = "모임 설명", example = "자유로운 모임입니다.")
    private String description;

    @Schema(description = "승인 방법", example = "FREE")
    @NotNull(message = "approvalMethod cannot be null")
    private ApprovalMethod approvalMethod;

    @Schema(description = "최대 수용 인원", example = "50")
    @NotNull(message = "maximumPeople cannot be null")
    private int maximumPeople;

    private List<String> tagList;

    public Club toEntity(Long hostId, String mainImageUrl) {
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
