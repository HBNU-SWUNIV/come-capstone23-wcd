package com.wcd.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserEvaluation {
    private Long userId;

    private List<Integer> score;

    private List<String> review;
}
