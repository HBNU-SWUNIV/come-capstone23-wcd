package com.wcd.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String loginId;
    private String name;
    private String phoneNumber;
    private Date birthday;
    private String gender;
    private Date createdAt;
    private boolean onlineStatus;
    private String profileImage;
}
