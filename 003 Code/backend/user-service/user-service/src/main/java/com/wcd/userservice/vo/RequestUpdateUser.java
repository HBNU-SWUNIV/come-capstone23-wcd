package com.wcd.userservice.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class RequestUpdateUser {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "PhoneNumber cannot be null")
    private String phoneNumber;

    @NotNull(message = "BirthDay cannot be null")
    private Date birthday;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    private String profile_image;
}
