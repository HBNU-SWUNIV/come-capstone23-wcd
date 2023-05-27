package com.wcd.userservice.service.user;

import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.dto.user.UserDto;
import com.wcd.userservice.dto.userevalution.UserEvaluationDto;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.dto.user.request.RequestUserEvaluation;

public interface UserService {


    ResponseUserById getUserById(Long userId);

    Long updateUserById(Long userId, RequestUpdateUser requestUpdateUser);

    void deleteUser(Long userId);


}
