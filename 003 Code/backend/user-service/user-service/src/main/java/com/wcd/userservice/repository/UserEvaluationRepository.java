package com.wcd.userservice.repository;

import com.wcd.userservice.entity.UserEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEvaluationRepository extends JpaRepository<UserEvaluation, Long> {
    UserEvaluation findByUserId(Long userId);
}
