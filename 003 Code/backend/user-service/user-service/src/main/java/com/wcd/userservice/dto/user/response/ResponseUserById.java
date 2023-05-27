package com.wcd.userservice.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.wcd.userservice.dto.club.ResponseClub;
import com.wcd.userservice.entity.User;
import com.wcd.userservice.enums.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserById {
    private Long id;
    private String loginId;
    private String name;
    private String phoneNumber;
    private LocalDate birthday;
    private Gender gender;
    private String profileImage;

    @Builder
    public ResponseUserById(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.birthday = user.getBirthday();
        this.gender = user.getGender();
        this.profileImage = user.getProfileImage();
    }
}
