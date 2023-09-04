package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "회원가입 요청")
@Getter
@AllArgsConstructor
public class RequestSignUp {

//    @Schema(description = "프로필 이미지")
//    private MultipartFile profileImage;

    @Schema(description = "로그인 아이디", example = "id1234", minLength = 3)
    @NotNull(message = "Id cannot be null")
    @Size(min = 2, message = "login_id not be less than two characters")
    private String loginId;

    @Schema(description = "패스워드", example = "pwd1234", minLength = 3)
    @NotNull(message = "Password cannot be null")
    @Size(min = 2, message = "Password not be less than tn o characters")
    private String password;

    @Schema(description = "이름", example = "홍길동", minLength = 3)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @Schema(description = "휴대전화 번호", example = "010-1234-5678")
    @NotNull(message = "PhoneNumber cannot be null")
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "Invalid phone number")
    private String phoneNumber;

    @Schema(description = "생일", example = "2000-01-01")
    @NotNull(message = "BirthDay cannot be null")
    private LocalDate birthDay;

    @Schema(description = "성별", example = "남")
    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    public Users toEntity(String encryptedPwd) {
        Users user = Users.builder()
                .loginId(loginId)
                .encryptedPwd(encryptedPwd)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthDay(birthDay)
                .gender(gender)
//                .profileImage(profileImageUrl)
                .build();

        return user;
    }
}
