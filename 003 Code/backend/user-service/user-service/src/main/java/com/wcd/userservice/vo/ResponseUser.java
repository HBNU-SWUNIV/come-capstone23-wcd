package com.wcd.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.userservice.dto.UserEvaluationDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private int id;
    private String loginId;
    private String name;
    private String phoneNumber;
    private LocalDate birthday;
    private String gender;
    private String profileImage;
    private LocalDateTime createdAt;
    private UserEvaluationDto userEvaluation;
    private List<ResponseClub> joinClubList;
}
