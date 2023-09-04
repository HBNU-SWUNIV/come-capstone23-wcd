package com.wcd.userservice.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "사용자 정보(user-id) 응답")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserById {
    @Schema(description = "유저 번호", example = "1")
    private Long id;

    @Schema(description = "로그인 아이디", example = "id1234")
    private String loginId;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "휴대전화 번호")
    private String phoneNumber;

    @Schema(description = "생일")
    private LocalDate birthday;

    @Schema(description = "성별")
    private Gender gender;

    @Schema(description = "프로필 이미지 URL")
    private String profileImage;

    @Builder
    public ResponseUserById(Users user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.birthday = user.getBirthDay();
        this.gender = user.getGender();
        this.profileImage = user.getProfileImage();
    }
}
