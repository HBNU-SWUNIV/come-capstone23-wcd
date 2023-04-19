package com.wcd.userservice.service;

import com.wcd.userservice.dto.RegenerateTokenDto;
import com.wcd.userservice.dto.TokenDto;
import com.wcd.userservice.dto.UserDto;
import com.wcd.userservice.dto.UserEvaluationDto;
import com.wcd.userservice.vo.RequestUpdateUser;
import org.springframework.security.core.userdetails.UserDetailsService;

// Authentication에 인증하기 위한 자격으로 들어가려면 UserDetailService를 상속받아 구현해줘야함
public interface UserService{
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    UserDto updateUserById(Long userId, RequestUpdateUser requestUpdateUser);

    void deleteUser(Long userId);

    UserEvaluationDto getUserEvaluationByUserId(Long userId);

    TokenDto regenerateToken(RegenerateTokenDto refreshTokenDto);

    void logout(TokenDto tokenDto);
}
