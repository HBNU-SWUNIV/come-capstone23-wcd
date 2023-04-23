package com.wcd.userservice.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserEvaluation {
    @NotNull(message = "score cannot be null")
    private int score;

    private String review;
}
