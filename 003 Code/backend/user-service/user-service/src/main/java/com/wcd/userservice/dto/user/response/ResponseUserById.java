package com.wcd.userservice.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    public ResponseUserById(Users user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.birthday = user.getBirthday();
        this.gender = user.getGender();
        this.profileImage = user.getProfileImage();
    }
}
