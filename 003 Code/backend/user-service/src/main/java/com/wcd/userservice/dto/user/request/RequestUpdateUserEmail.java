package com.wcd.userservice.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestUpdateUserEmail {
    String newEmail;
}
