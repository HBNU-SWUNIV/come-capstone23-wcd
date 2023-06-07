package com.wcd.userservice.service.user;

import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;


public interface UserService {

    ResponseUserById getUserById(Long userId);
    Long updateUserById(Long userId, RequestUpdateUser requestUpdateUser);
    void deleteUser(Long userId);
}
