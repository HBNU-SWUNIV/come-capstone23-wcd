package com.wcd.userservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserEvaluationDto {

    private Long id;

    private Long userId;

    private List<Integer> score;

    private List<String> review;
}
