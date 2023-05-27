package com.wcd.userservice.repository;

import com.wcd.userservice.entity.UserEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEvaluationRepository extends JpaRepository<UserEvaluation, Long> {
    List<UserEvaluation> findByUserId(Long userId);
}
