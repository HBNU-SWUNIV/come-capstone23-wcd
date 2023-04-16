package com.wcd.userservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserEvaluation {

    private Long id;

    private List<Integer> score;

    private List<String> review;
}
