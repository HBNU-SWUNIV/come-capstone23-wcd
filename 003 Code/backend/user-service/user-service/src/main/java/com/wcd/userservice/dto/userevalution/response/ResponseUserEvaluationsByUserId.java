package com.wcd.userservice.dto.userevalution.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.userservice.entity.UserEvaluation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserEvaluationsByUserId {
    private List<Integer> scores = new ArrayList<>();

    private List<String> reviews = new ArrayList<>();

    @Builder
    public ResponseUserEvaluationsByUserId(List<UserEvaluation> userEvaluationList) {
        for (UserEvaluation userEvaluation : userEvaluationList) {
            scores.add(userEvaluation.getScore());
            reviews.add(userEvaluation.getReview());
        }
    }
}
