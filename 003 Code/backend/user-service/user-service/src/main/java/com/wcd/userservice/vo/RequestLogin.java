package com.wcd.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @NotNull(message = "login_id cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String loginId;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equals or grater than  characters")
    private String password;
}
