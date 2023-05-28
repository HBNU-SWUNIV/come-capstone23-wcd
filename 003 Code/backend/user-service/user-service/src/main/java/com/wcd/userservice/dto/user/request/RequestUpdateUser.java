package com.wcd.userservice.dto.user.request;

import com.wcd.userservice.enums.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateUser {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than w characters")
    private String name;

    @NotNull(message = "PhoneNumber cannot be null")
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "Invalid phone number")
    private String phoneNumber;

    @NotNull(message = "BirthDay cannot be null")
    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    private String profileImage;
}
