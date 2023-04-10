package com.wcd.userservice.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String encryptedPwd;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private String gender;

    private boolean onlineStatus;

    private String profileImage;
}
