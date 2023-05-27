package com.wcd.userservice.controller;

import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.service.user.UserService;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    private final Environment env;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("local.server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    // 회원 조회 (user-id)
    @GetMapping("/user/{user-id}")
    public ResponseEntity<ResponseUserById> getUserById(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    // 회원 수정 (user-id)
    @PutMapping("/user/{user-id}")
    public ResponseEntity<Long> updateUser(@PathVariable("user-id") Long userId, @Valid @RequestBody RequestUpdateUser requestUpdateUser) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId, requestUpdateUser));
    }

    // 회원 삭제 (user-id)
    @DeleteMapping("/user/{user-id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
