package com.wcd.userservice.service;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userEntity = userRepository.findByEmail(username);

        // 해당하는 사용자가 없다면
        if(userEntity == null)
            // spring security-core에 사용자 정보를 담을 수 있는 UsernamePasswordAuthenticationToken이 있음
            // 마찬가지로 사용자 검색이 안되었을 때, 발생할 수 있는 예외 클래스도 제공
            throw new UsernameNotFoundException(username);

        // User: Spring Security에서 제공해주는 User 모델
        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true, new ArrayList<>());
    }
}
