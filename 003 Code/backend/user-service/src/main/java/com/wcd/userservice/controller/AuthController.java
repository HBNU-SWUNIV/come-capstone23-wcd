package com.wcd.userservice.controller;

import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 인증관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "토큰 재발급", description = "만료된 토큰 재발급 받기")
    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@RequestBody RegenerateTokenDto refreshTokenDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.regenerateToken(refreshTokenDto));
    }

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@Valid @RequestBody RequestSignUp requestSignUp) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(requestSignUp));
    }

    @Operation(summary = "로그아웃", description = "로그아웃 API")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenDto tokenDto) {
        authService.logout(tokenDto);

        return ResponseEntity.noContent().build();
    }
}
