package com.wcd.userservice.security;

import com.wcd.userservice.security.jwt.JwtTokenProvider;
import com.wcd.userservice.security.oauth.CustomOAuth2UserService;
import com.wcd.userservice.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.wcd.userservice.security.oauth.OAuth2AuthenticationFailureHandler;
import com.wcd.userservice.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.wcd.userservice.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    // 권한과 관련한 메서드
    // HttpSecurity: HTTP 요청에 대한 보안 구성 지정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // 사용자 인증 매니저, 인증 매니저는 사용자의 인증 정보를 확인
        AuthenticationManager authenticationManager = getAuthenticationManager(http);

        http
            .csrf().disable()
            .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/**").permitAll()
                .and()
                    .authenticationManager(authenticationManager)
                    .addFilter(getAuthenticationFilter(authenticationManager));

        http.
                oauth2Login() // OAuth2 로그인 설정 시작점
                    .authorizationEndpoint().baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
                .and()
                    .redirectionEndpoint()
                    .baseUri("/login/oauth2/code/**")
                .and()
                    .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                    .userService(customOAuth2UserService) // OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        return http.build();
    }

    private AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
        // 인증 매니저를 구성하는 빌더 클래스
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // userDetailService: 사용자 인증 정보를 검색할 때 사용하는 서비스 (userService)
        // passwordEncoder: 패스워드 인코딩을 위해 사용
        authenticationManagerBuilder.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, myUserDetailsService, env, redisTemplate, jwtTokenProvider);
    }
}
