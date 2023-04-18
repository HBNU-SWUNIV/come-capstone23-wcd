package com.wcd.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcd.userservice.dto.TokenDto;
import com.wcd.userservice.dto.UserDto;
import com.wcd.userservice.service.UserService;
import com.wcd.userservice.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// Spring Security를 이용한 로그인 요청 발생 시 작업을 처리해 주는 Custom Filter 클래스
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    RedisTemplate<String, String> redisTemplate;

    public AuthenticationFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super.setAuthenticationManager(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try{
            /*
                클라이언트에서 전송한 로그인 정보를 읽어옴
                - ObjectMapper: JSON 데이터를 자바 객체로 변환하는 Jackson 라이브러리 클래스
                - request.getInputStream(): HTTP 요청 바디(body)에 포함된 데이터를 읽어오기 위한 메서드
             */
            RequestLogin requestLogin = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            // getAuthenticationManager: 인증 처리 메서드
            return getAuthenticationManager().authenticate(
                    /*
                        사용자가 입력했던 login_id와 password 값을 spring security에서 사용할 수 있는 형태의 값으로 변환하기 위해서
                        UsernamePasswordAuthenticationToken 형태로 변환
                     */
                    new UsernamePasswordAuthenticationToken(
                            requestLogin.getLoginId(),
                            requestLogin.getPassword(),
                            // 권한과 관련된 값
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        /*
            User: 인증에 성공한 사용자 정보를 담는 클래스
            (User) authResult.getPrincipal()를 통해 사용자 정보를 가져온 뒤 getUsername()을 통해 사용자의 아이디 정보를 가져온다.
         */
        String loginId = ((User) authResult.getPrincipal()).getUsername();
        // 여기서는 loginId로 토큰 값을 만들 것이 아니라 userId를 통해 토큰을 만들 것이기 때문에 loginId 통해 사용자 정보를 가져온다.
        UserDto userDetails = userService.getUserDetailsByLoginId(loginId);
        String userId = userDetails.getUserId();

        String refresh_token = generateRefreshToken(userId);

        TokenDto tokenDto = new TokenDto(
                generateAccessToken(userId),
                refresh_token
        );

        redisTemplate.opsForValue().set(
                userId,
                refresh_token,
                Long.parseLong(env.getProperty("refresh_token.expiration_time")),
                TimeUnit.MICROSECONDS
        );

        // 응답헤더에 token과 userId 추가
        response.addHeader("access_token", tokenDto.getAccess_token());
        response.addHeader("refresh_token", tokenDto.getRefresh_token());
        // userId를 반환시켜주는 이유는 우리가 가지고 있는 token과 userId가 동일한지 확인하기 위함
        response.addHeader("userId", userDetails.getUserId());
    }

    public String generateAccessToken(String userId) {
        Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                // JWT 토큰의 subject를 설정
                .setSubject(userId)
                // JWT 토큰의 만료 시간 설정(현재 시간 + token.expiration_time 값)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("access_token.expiration_time"))))
                // JWT 토큰에 서명 추가
                .signWith(secretKey, SignatureAlgorithm.HS512)
                // JWT 토큰을 문자열로 변환
                .compact();

        return token;
    }

    public String generateRefreshToken(String userId) {
        Key secretKey = Keys.hmacShaKeyFor(env.getProperty("refresh_token.secret").getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                // JWT 토큰의 subject를 설정
                .setSubject(userId)
                // JWT 토큰의 만료 시간 설정(현재 시간 + token.expiration_time 값)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("refresh_token.expiration_time"))))
                // JWT 토큰에 서명 추가
                .signWith(secretKey, SignatureAlgorithm.HS512)
                // JWT 토큰을 문자열로 변환
                .compact();

        return token;
    }
}
