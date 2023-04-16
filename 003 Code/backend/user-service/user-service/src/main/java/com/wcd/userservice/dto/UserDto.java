package com.wcd.userservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String loginId;
    private String password;
    private String encryptedPwd;
    private String name;
    private String phoneNumber;
    private LocalDate birthday;
    private String gender;
    private String profileImage;
    private LocalDateTime createdAt;

    private UserEvaluation userEvaluation;
    private List<Integer> joinClubList;
}
