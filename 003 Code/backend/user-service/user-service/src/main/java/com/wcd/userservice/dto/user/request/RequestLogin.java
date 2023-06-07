package com.wcd.userservice.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "로그인 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogin {

    @Schema(description = "로그인 아이디", example = "id1234", minLength = 3)
    @NotNull(message = "login_id cannot be null")
    @Size(min = 2, message = "login_id not be less than 2 characters")
    @Email
    private String loginId;

    @Schema(description = "패스워드", example = "pwd1234", minLength = 9)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equals or grater than 8 characters")
    private String password;
}
