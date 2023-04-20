package com.wcd.userservice.vo.request;

import com.wcd.userservice.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestUpdateUser {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "PhoneNumber cannot be null")
    private String phoneNumber;

    @NotNull(message = "BirthDay cannot be null")
    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    private String profile_image;
}
