package com.wcd.userservice.controller;

import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {
    private final AuthService authService;

    // 토큰 재발급
    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@RequestBody RegenerateTokenDto refreshTokenDto) {
        TokenDto tokenDto = authService.regenerateToken(refreshTokenDto);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@Valid @RequestBody RequestSignUp requestSignUp) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(requestSignUp));
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenDto tokenDto) {
        authService.logout(tokenDto);

        return ResponseEntity.noContent().build();
    }
}
