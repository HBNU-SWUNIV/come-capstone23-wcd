package com.wcd.userservice.service.user;

import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.entity.User;
import com.wcd.userservice.exception.CustomException;
import com.wcd.userservice.repository.UserEvaluationRepository;
import com.wcd.userservice.repository.UserRepository;
import com.wcd.userservice.security.jwt.JwtTokenProvider;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    @Override
    public ResponseUserById getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseUserById.builder().user(user).build();
    }

    @Transactional
    @Override
    public Long updateUserById(Long userId, RequestUpdateUser requestUpdateUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.updateUser(requestUpdateUser);

        return userId;
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
    }
}
