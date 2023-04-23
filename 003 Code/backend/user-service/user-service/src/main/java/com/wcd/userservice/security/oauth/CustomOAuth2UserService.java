package com.wcd.userservice.security.oauth;

import com.wcd.userservice.security.oauth.entity.OAuth;
import com.wcd.userservice.security.oauth.dto.OAuthAttributes;
import com.wcd.userservice.security.oauth.repository.OAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

// OAuth 로그인 이후 가져온 사용자 정보를 기반으로 가입 및 정보 수정, 세션 저장 등의 기능 지원
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private OAuthRepository oAuthRepository;

    // OAuth 로그인을 통해 얻은 유저 정보를 담고 있는 OAuth2User를 가져온다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId: 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 여러 소셜 로그인 서비스를 연동할 때 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        /*
            - OAuth2 로그인 진행 시 키가 되는 필드 값, Primary Key와 같은 의미
            - 구글의 경우 기본적으로 코드를 지원하지만, 네이버나 카카오 등은 기본 지원하지 않음
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        OAuth user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private OAuth saveOrUpdate(OAuthAttributes attributes) {
        OAuth user = oAuthRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getProvider()))
                .orElse(attributes.toEntity());

        return oAuthRepository.save(user);
    }
}
