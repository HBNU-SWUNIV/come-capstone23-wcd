package com.wcd.userservice.security.oauth.repository;

import com.wcd.userservice.security.oauth.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {
    // 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입한 사용자인지 판단하는 메서드
    Optional<OAuth> findByEmail(String email);
}
