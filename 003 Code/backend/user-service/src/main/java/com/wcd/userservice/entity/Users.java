package com.wcd.userservice.entity;

import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String encryptedPwd;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private String profileImage;

    @Builder
    public Users(String loginId, String encryptedPwd, String name, String phoneNumber, LocalDate birthDay, Gender gender, String profileImage) {
        this.loginId = loginId;
        this.encryptedPwd = encryptedPwd;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public void updateUser(RequestUpdateUser requestUpdateUser, String profileImageUrl) {
        if (StringUtils.hasText(requestUpdateUser.getName())) {
            this.name = requestUpdateUser.getName();
        }

        if (StringUtils.hasText(requestUpdateUser.getPhoneNumber())) {
            this.phoneNumber = requestUpdateUser.getPhoneNumber();
        }

        if (requestUpdateUser.getBirthDay() != null) {
            this.birthDay = requestUpdateUser.getBirthDay();
        }

        if (requestUpdateUser.getGender() != null) {
            this.gender = requestUpdateUser.getGender();
        }

        if (StringUtils.hasText(profileImageUrl)) {
            this.profileImage = profileImageUrl;
        }
    }
}
