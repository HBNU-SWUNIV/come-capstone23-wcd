package com.wcd.userservice.service.userevaluation;

import com.wcd.userservice.dto.user.request.RequestUserEvaluation;
import com.wcd.userservice.dto.userevalution.response.ResponseUserEvaluationsByUserId;

public interface UserEvaluationService {
    Long createUserEvaluationByUserId(Long userId, RequestUserEvaluation requestUserEvaluation);

    ResponseUserEvaluationsByUserId getUserEvaluationByUserId(Long userId);
}
