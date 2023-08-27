package com.wcd.userservice.security.oauth;

import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.security.oauth.util.CookieUtils;
import com.wcd.userservice.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.wcd.userservice.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

// 사용자가 인증을 마친 후 리다이렉트할 URL을 결정하고, 쿠이와 인증 토큰을 생성하는 등의 역할
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        // 인증 성공 후 사용한 쿠키를 삭제
        clearAuthenticationAttributes(request, response);
        // 코드를 통해 결정된 URL로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 쿠키에서 redirect_uri 파라미터 값을 가져온다.
        // redirect_uri 파라미터 값은 로그인 성공 후 리다이렉트 될 URL을 의미
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        // redirect_uri 파라미터 값이 없을 경우 기본 리다이렉트 URL을 가져온다.
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String refresh_token = jwtTokenProvider.generateAccessToken(authentication);

        TokenDto tokenDto = new TokenDto(
                jwtTokenProvider.generateAccessToken(authentication),
                refresh_token
        );

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", tokenDto.getAccess_token())
                .queryParam("refresh_token", tokenDto.getRefresh_token())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}
