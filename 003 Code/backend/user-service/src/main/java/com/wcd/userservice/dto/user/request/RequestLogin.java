package com.wcd.userservice.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "로그인 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogin {

    @Schema(description = "로그인 아이디", example = "id1234", minLength = 5, maxLength = 20)
    @NotNull(message = "login_id cannot be null")
    @Size(min = 5, max = 20, message = "login_id not be less than 2 characters")
    private String loginId;

    @Schema(description = "패스워드", example = "pwd1234", minLength = 8)
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 20, message = "Password must be equals or grater than 8 characters")
    @Pattern(regexp = "^(?:(?=.*[a-zA-Z])(?=.*[\\W_])|(?=.*[a-zA-Z])(?=.*\\d)|(?=.*\\d)(?=.*[\\W_])).{8,}$",
            message = "Password must be at least 8 characters long and combine at least two of the following: letters, numbers, and special symbols.")
    private String password;
}
