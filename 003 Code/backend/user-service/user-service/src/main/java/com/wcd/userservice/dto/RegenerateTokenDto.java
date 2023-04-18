package com.wcd.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegenerateTokenDto {
    private String refresh_token;
}
