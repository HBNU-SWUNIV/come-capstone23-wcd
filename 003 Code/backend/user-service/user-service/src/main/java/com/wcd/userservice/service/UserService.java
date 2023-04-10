package com.wcd.userservice.service;

import com.wcd.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

// Authentication에 인증하기 위한 자격으로 들어가려면 UserDetailService를 상속받아 구현해줘야함
public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserDetailsByLoginId(String loginId);

    UserDto getUserByUserId(String userId);
}
