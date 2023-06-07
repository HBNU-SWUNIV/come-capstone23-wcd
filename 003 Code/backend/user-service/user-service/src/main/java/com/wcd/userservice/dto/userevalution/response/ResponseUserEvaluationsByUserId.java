package com.wcd.userservice.dto.userevalution.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.userservice.entity.UserEvaluation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "유저 평가 응답")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserEvaluationsByUserId {
    @Schema(description = "유저 평가 정수")
    private List<Integer> scores = new ArrayList<>();

    @Schema(description = "유저 평가 리뷰")
    private List<String> reviews = new ArrayList<>();

    @Builder
    public ResponseUserEvaluationsByUserId(List<UserEvaluation> userEvaluationList) {
        for (UserEvaluation userEvaluation : userEvaluationList) {
            scores.add(userEvaluation.getScore());
            reviews.add(userEvaluation.getReview());
        }
    }
}
