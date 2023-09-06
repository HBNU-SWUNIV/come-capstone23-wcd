package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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

    @Schema(description = "이름", example = "홍길동", minLength = 2)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than w characters")
    private String name;

    @Schema(description = "휴대전화 번호", example = "01012345678")
    @NotNull(message = "PhoneNumber cannot be null")
    private String phoneNumber;
}
