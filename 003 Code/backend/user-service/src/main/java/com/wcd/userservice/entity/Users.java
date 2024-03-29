package com.wcd.userservice.entity;

import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String encryptedPwd;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private String profileImage;

    @Builder
    public Users(String email, String encryptedPwd, String name, String phoneNumber, LocalDate birthday, Gender gender, String profileImage) {
        this.email = email;
        this.encryptedPwd = encryptedPwd;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updatePassword(String newPassword) {
        this.encryptedPwd = newPassword;
    }

    public void updateUser(RequestUpdateUser requestUpdateUser, String profileImageUrl) {
        if (StringUtils.hasText(requestUpdateUser.getName())) {
            this.name = requestUpdateUser.getName();
        }

        if (StringUtils.hasText(requestUpdateUser.getPhoneNumber())) {
            this.phoneNumber = requestUpdateUser.getPhoneNumber();
        }

        if (StringUtils.hasText(profileImageUrl)) {
            this.profileImage = profileImageUrl;
        }
    }
}
