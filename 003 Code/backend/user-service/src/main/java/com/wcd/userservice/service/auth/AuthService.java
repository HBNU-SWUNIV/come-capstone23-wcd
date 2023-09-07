package com.wcd.userservice.service.auth;

import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;

public interface AuthService {
    Long signUp(RequestSignUp requestSignUp);

    TokenDto regenerateToken(RegenerateTokenDto refreshTokenDto);

    void logout(TokenDto tokenDto);

    void sendAuthenticationEmail(String email);

    boolean verifyAuthenticationCode(String email, String code);
}
