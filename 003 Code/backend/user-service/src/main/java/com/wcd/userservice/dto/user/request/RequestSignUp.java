package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;

@Schema(description = "회원가입 요청")
@Getter
@AllArgsConstructor
public class RequestSignUp {

//    @Schema(description = "프로필 이미지")
//    private MultipartFile profileImage;

    @Schema(description = "이메일", example = "example@example.org")
    @NotNull(message = "Email cannot be null")
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",
            message = "Email format is not correct")
    private String email;

    @Schema(description = "패스워드", example = "pwd1234!", minLength = 8, maxLength = 20)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 20, message = "Password must be between 8 to 20 characters long.")
    @Pattern(regexp = "^(?:(?=.*[a-zA-Z])(?=.*[\\W_])|(?=.*[a-zA-Z])(?=.*\\d)|(?=.*\\d)(?=.*[\\W_])).{8,}$",
            message = "Password must be at least 8 characters long and combine at least two of the following: letters, numbers, and special symbols.")
    private String password;

    @Schema(description = "이름", example = "홍길동", minLength = 2)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    @NotNull(message = "PhoneNumber cannot be null")
    private String phoneNumber;

    @Schema(description = "생일", example = "2000-01-01")
    @Past(message = "Birth date must be in the past")
    @NotNull(message = "BirthDay cannot be null")
    private LocalDate birthday;

    @Schema(description = "성별", example = "남")
    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    public Users toEntity(String encryptedPwd) {
        Users user = Users.builder()
                .email(email)
                .encryptedPwd(encryptedPwd)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthday(birthday)
                .gender(gender)
//                .profileImage(profileImageUrl)
                .build();

        return user;
    }
}
