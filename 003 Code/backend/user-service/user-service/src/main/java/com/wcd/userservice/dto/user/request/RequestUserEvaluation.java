package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.entity.UserEvaluation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserEvaluation {
    @NotNull(message = "score cannot be null")
    private int score;

    private String review;

    public UserEvaluation toEntity(Users user) {
        return UserEvaluation.builder()
                .user(user)
                .score(score)
                .review(review)
                .build();
    }
}
