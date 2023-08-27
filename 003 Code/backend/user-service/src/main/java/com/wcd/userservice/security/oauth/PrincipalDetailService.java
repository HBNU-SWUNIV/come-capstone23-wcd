package com.wcd.userservice.security.oauth;

import com.wcd.userservice.security.oauth.entity.OAuth;
import com.wcd.userservice.security.oauth.dto.SessionUser;
import com.wcd.userservice.security.oauth.repository.OAuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {
    private final OAuthRepository repository;

    public PrincipalDetailService(OAuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuth principal = repository.findByEmail(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다");
                });
        return new SessionUser(principal);
    }
}
