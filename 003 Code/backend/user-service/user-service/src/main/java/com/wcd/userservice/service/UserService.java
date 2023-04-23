package com.wcd.userservice.service;

import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.dto.UserDto;
import com.wcd.userservice.dto.UserEvaluationDto;
import com.wcd.userservice.vo.request.RequestUpdateUser;
import com.wcd.userservice.vo.request.RequestUserEvaluation;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    UserDto updateUserById(Long userId, RequestUpdateUser requestUpdateUser);

    void deleteUser(Long userId);

    UserEvaluationDto getUserEvaluationByUserId(Long userId);

    TokenDto regenerateToken(RegenerateTokenDto refreshTokenDto);

    void logout(TokenDto tokenDto);

    UserEvaluationDto createUserEvaluationByUserId(Long userId, RequestUserEvaluation requestUserEvaluation);
}
