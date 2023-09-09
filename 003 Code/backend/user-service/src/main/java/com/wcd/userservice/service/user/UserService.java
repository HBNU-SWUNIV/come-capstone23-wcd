package com.wcd.userservice.service.user;

import com.wcd.userservice.dto.user.request.RequestUpdateUserEmail;
import com.wcd.userservice.dto.user.request.RequestUpdateUserPassword;
import com.wcd.userservice.dto.user.request.RequestUsernames;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.dto.user.response.ResponseUsernames;


public interface UserService {

    ResponseUserById getUserById(Long userId);
    Long updateUserById(Long userId, RequestUpdateUser requestUpdateUser);
    Long updateUserEmail(Long userId, RequestUpdateUserEmail requestUpdateEmail);
    Long updateUserPassword(Long userId, RequestUpdateUserPassword requestUpdateUserPassword);
    void deleteUser(Long userId);
    ResponseUsernames getUserNamesByIds(RequestUsernames requestUsernames);
    String getUserNameById(Long userId);

    Long getUserIdByName(String name);
}
