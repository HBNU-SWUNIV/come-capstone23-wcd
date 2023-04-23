package com.wcd.userservice.security.oauth.entity;

import com.wcd.userservice.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// OAuth 로그인을 통해 얻어온 유저 정보를 매핑하기 위한 도메인
@Getter
@NoArgsConstructor
@Entity
public class OAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    @Builder
    public OAuth(Long id, String name, String email, String password, Role role, String provider) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;

    }

    public OAuth update(String name, String provider) {
        this.name = name;
        this.provider = provider;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
