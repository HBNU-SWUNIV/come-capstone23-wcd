package com.wcd.userservice.vo.request;

import com.wcd.userservice.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

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
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull(message = "BirthDay cannot be null")
    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;
}
