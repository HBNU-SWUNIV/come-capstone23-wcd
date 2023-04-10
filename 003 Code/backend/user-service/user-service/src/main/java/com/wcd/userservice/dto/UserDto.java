package com.wcd.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String loginId;
    private String password;
    private String userId;
    private String name;
    private String phoneNumber;
    private Date birthday;
    private String gender;
    private Date createdAt;
    private boolean onlineStatus;
    private String profileImage;

    private String encryptedPwd;
}
