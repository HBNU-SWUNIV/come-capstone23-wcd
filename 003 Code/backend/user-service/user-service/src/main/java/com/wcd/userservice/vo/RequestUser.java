package com.wcd.userservice.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class RequestUser {

    @NotNull(message = "Id cannot be null")
    @Size(min = 2, message = "login_id not be less than two characters")
    private String loginId;

    @NotNull(message = "Password cannot be null")
    @Size(min = 2, message = "Password not be less than two characters")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "PhoneNumber cannot be null")
    private String phoneNumber;

    @NotNull(message = "BirthDay cannot be null")
    private Date birthday;

    @NotNull(message = "Gender cannot be null")
    private String gender;
}
