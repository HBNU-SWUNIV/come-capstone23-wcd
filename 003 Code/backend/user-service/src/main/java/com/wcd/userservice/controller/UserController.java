package com.wcd.userservice.controller;

import com.wcd.userservice.dto.user.request.RequestUpdateUserPassword;
import com.wcd.userservice.dto.user.request.RequestUsernames;
import com.wcd.userservice.dto.user.response.ResponseUsernames;
import com.wcd.userservice.service.user.UserService;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.PATCH;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    private final Environment env;
    private final UserService userService;

    @Operation(summary = "회원 정보 조회", description = "해당 user-id의 회원 정보 조회")
    @GetMapping("/user/{user-id}")
    public ResponseEntity<ResponseUserById> getUserById(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @Operation(summary = "회원 정보 수정", description = "해당 user-id의 회원 정보 수정")
    @PatchMapping("/user/{user-id}")
    public ResponseEntity<Long> updateUser(@PathVariable("user-id") Long userId, @Valid @ModelAttribute RequestUpdateUser requestUpdateUser) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(userId, requestUpdateUser));
    }

    @PatchMapping("/user/{user-id}/password")
    public ResponseEntity<Long> updateUserPassword(@RequestHeader("user-id") Long userId, RequestUpdateUserPassword requestUpdateUserPassword) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserPassword(userId, requestUpdateUserPassword));
    }

    @Operation(summary = "회원 삭제", description = "해당 user-id의 회원 삭제")
    @DeleteMapping("/user/{user-id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 이름 목록 조회", description = "해당 회원들의 user-id의 user-name 조회")
    @PostMapping("/user/userNames")
    public ResponseEntity<ResponseUsernames> getUserNamesByIds(@RequestBody RequestUsernames requestUsernames) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserNamesByIds(requestUsernames));
    }

    @Operation(summary = "회원 이름 단건 조회", description = "해당 user-id의 userName 조회")
    @GetMapping("/user/{user-id}/userName")
    public ResponseEntity<String> getUsernameById(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserNameById(userId));
    }
}
