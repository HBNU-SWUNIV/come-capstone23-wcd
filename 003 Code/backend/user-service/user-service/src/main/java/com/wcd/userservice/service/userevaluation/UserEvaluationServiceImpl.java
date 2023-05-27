package com.wcd.userservice.service.userevaluation;

import com.wcd.userservice.dto.user.request.RequestUserEvaluation;
import com.wcd.userservice.dto.userevalution.response.ResponseUserEvaluationsByUserId;
import com.wcd.userservice.entity.User;
import com.wcd.userservice.entity.UserEvaluation;
import com.wcd.userservice.repository.UserEvaluationRepository;
import com.wcd.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserEvaluationServiceImpl implements UserEvaluationService{
    private final UserRepository userRepository;
    private final UserEvaluationRepository userEvaluationRepository;

    @Override
    public Long createUserEvaluationByUserId(Long userId, RequestUserEvaluation requestUserEvaluation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found userId: " + userId));

        return userEvaluationRepository.save(requestUserEvaluation.toEntity(user)).getId();
    }

    @Override
    public ResponseUserEvaluationsByUserId getUserEvaluationByUserId(Long userId) {
        List<UserEvaluation> userEvaluationList = userEvaluationRepository.findByUserId(userId);

        return ResponseUserEvaluationsByUserId.builder().userEvaluationList(userEvaluationList).build();
    }
}
