package com.wcd.userservice.repository;

import com.wcd.userservice.entity.UserEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEvaluationRepository extends JpaRepository<UserEvaluationEntity, Long> {
    UserEvaluationEntity findByUserId(Long userId);
}
