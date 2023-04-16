package com.wcd.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.userservice.dto.UserEvaluation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private UserEvaluation userEvaluation;
}
