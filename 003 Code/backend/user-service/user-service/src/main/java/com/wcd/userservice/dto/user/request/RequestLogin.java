package com.wcd.userservice.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogin {

    @NotNull(message = "login_id cannot be null")
    @Size(min = 2, message = "login_id not be less than 2 characters")
    @Email
    private String loginId;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equals or grater than 8 characters")
    private String password;
}
