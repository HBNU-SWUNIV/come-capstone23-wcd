package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Schema(description = "사용자 정보 수정 요청")
@Getter
@AllArgsConstructor
public class RequestUpdateUser {
    MultipartFile profileImage;

    @Schema(description = "이름", example = "홍길동", minLength = 3)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than w characters")
    private String name;

    @Schema(description = "휴대전화 번호", example = "010-1234-5678")
    @NotNull(message = "PhoneNumber cannot be null")
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "Invalid phone number")
    private String phoneNumber;

    @Schema(description = "생일", example = "2000-01-01")
    @NotNull(message = "BirthDay cannot be null")
    private LocalDate birthday;

    @Schema(description = "성별", example = "남")
    @NotNull(message = "Gender cannot be null")
    private Gender gender;
}
