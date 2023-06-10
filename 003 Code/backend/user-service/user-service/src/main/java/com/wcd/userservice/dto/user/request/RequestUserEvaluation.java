package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.entity.UserEvaluation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 평가 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserEvaluation {
    @Schema(description = "사용자 점수", example = "10")
    @NotNull(message = "score cannot be null")
    private int score;

    @Schema(description = "사용자 리뷰", example = "좋아요~")
    private String review;

    public UserEvaluation toEntity(Users user) {
        return UserEvaluation.builder()
                .user(user)
                .score(score)
                .review(review)
                .build();
    }
}
