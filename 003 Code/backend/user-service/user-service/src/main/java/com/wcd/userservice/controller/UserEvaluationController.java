package com.wcd.userservice.controller;

import com.wcd.userservice.dto.user.request.RequestUserEvaluation;
import com.wcd.userservice.dto.userevalution.response.ResponseUserEvaluationsByUserId;
import com.wcd.userservice.service.userevaluation.UserEvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserEvaluationController {
    private final UserEvaluationService userEvaluationService;

    @PostMapping("/evaluation/user/{user-id}/")
    public ResponseEntity<Long> createUserEvaluation(@PathVariable("user-id") Long userId, @Valid @RequestBody RequestUserEvaluation requestUserEvaluation) {
        return ResponseEntity.status(HttpStatus.OK).body(userEvaluationService.createUserEvaluationByUserId(userId, requestUserEvaluation));
    }

    @GetMapping("/evaluation/user/{user-id}")
    public ResponseEntity<ResponseUserEvaluationsByUserId> getUserEvaluation(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userEvaluationService.getUserEvaluationByUserId(userId));
    }
}
