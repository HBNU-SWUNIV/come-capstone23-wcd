package com.wcd.userservice.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenWithUserIdDto {
    private String access_token;
    private String refresh_token;
    private String user_id;
}
