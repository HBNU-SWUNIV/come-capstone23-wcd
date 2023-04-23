package com.wcd.userservice.security.oauth.dto;

import com.wcd.userservice.security.oauth.entity.OAuth;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/*
    - 세션에 사용자 정보를 저장하기 위한 DTO 클래스
    - User 클래스를 그대로 사용하면 User 클래스는 엔티티이기 때문에 언제 다른 엔티티와 관게가 형성될지 모름
 */
@Getter
public class SessionUser implements UserDetails {
    private OAuth user;

    public SessionUser(OAuth user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();

        collections.add(() -> {
            return "ROLE_" + user.getRole();
        });

        return collections;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}