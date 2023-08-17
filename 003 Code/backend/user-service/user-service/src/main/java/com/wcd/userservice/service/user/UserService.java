package com.wcd.userservice.service.user;

import com.wcd.userservice.dto.user.request.RequestUsernames;
import com.wcd.userservice.dto.user.response.ResponseUserById;
import com.wcd.userservice.dto.user.request.RequestUpdateUser;
import com.wcd.userservice.dto.user.response.ResponseUsernames;


public interface UserService {

    ResponseUserById getUserById(Long userId);
    Long updateUserById(Long userId, RequestUpdateUser requestUpdateUser);
    void deleteUser(Long userId);
    ResponseUsernames getUserNamesByIds(RequestUsernames requestUsernames);
<<<<<<< HEAD

=======
>>>>>>> b738a79786a9b7ffc7e20ab9212949cefe1144b5
    String getUserNameById(Long userId);
}
