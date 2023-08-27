package com.wcd.userservice.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegenerateTokenDto {
    private String refresh_token;
}
