package com.wcd.clubservice.dto.club.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "모임 수정 요청")
@Getter
@AllArgsConstructor
public class RequestUpdateClub {

    @Schema(description = "모임 메인 사진")
    private MultipartFile multipartFile;

    @Schema(description = "모임 이름", example = "WCD 모임")
    @NotNull(message = "ClubName cannot be null")
    private String clubName;

    @Schema(description = "카테고리", example = "독서")
    @NotNull(message = "Category cannot be null")
    private String category;

    @Schema(description = "모임 설명", example = "자유로운 모임입니다.")
    private String description;

    @Schema(description = "최대 수용 인원", example = "50")
    @NotNull(message = "MaximumPeople cannot be null")
    private int maximumPeople;

    @Schema(description = "모집 여부", example = "True")
    @NotNull(message = "Recruitment cannot be null")
    private boolean recruitment;
}
