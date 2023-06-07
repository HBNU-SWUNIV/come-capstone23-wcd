package com.wcd.userservice.controller;

import com.wcd.userservice.dto.user.request.RequestUserEvaluation;
import com.wcd.userservice.dto.userevalution.response.ResponseUserEvaluationsByUserId;
import com.wcd.userservice.service.userevaluation.UserEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 평가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserEvaluationController {
    private final UserEvaluationService userEvaluationService;

    @Operation(summary = "유저 평가", description = "해당 user-id의 유저 평가")
    @PostMapping("/evaluation/user/{user-id}")
    public ResponseEntity<Long> createUserEvaluation(@PathVariable("user-id") Long userId, @Valid @RequestBody RequestUserEvaluation requestUserEvaluation) {
        return ResponseEntity.status(HttpStatus.OK).body(userEvaluationService.createUserEvaluationByUserId(userId, requestUserEvaluation));
    }

    @Operation(summary = "유저 평가 조회", description = "해당 user-id의 유저 평가 조회")
    @GetMapping("/evaluation/user/{user-id}")
    public ResponseEntity<ResponseUserEvaluationsByUserId> getUserEvaluation(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userEvaluationService.getUserEvaluationByUserId(userId));
    }
}
