package com.wcd.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcd.userservice.repository.UserRepository;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import com.wcd.userservice.security.jwt.JwtTokenProvider;
import com.wcd.userservice.security.jwt.dto.TokenWithUserIdDto;
import com.wcd.userservice.service.MyUserDetailsService;
import com.wcd.userservice.dto.user.request.RequestLogin;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

// Spring Security를 이용한 로그인 요청 발생 시 작업을 처리해 주는 Custom Filter 클래스
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private MyUserDetailsService myUserDetailsService;
    private Environment env;
    private RedisTemplate<String, String> redisTemplate;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                MyUserDetailsService myUserDetailsService,
                                Environment env,
                                RedisTemplate<String, String> redisTemplate,
                                JwtTokenProvider jwtTokenProvider,
                                UserRepository userRepository) {
        super.setAuthenticationManager(authenticationManager);
        this.myUserDetailsService = myUserDetailsService;
        this.env = env;
        this.redisTemplate = redisTemplate;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
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
                            requestLogin.getEmail(),
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
        String refresh_token = jwtTokenProvider.generateRefreshToken(authResult);

        TokenDto tokenDto = new TokenDto(
                jwtTokenProvider.generateAccessToken(authResult),
                refresh_token
        );

        TokenWithUserIdDto tokenWithUserIdDto = new TokenWithUserIdDto(
                jwtTokenProvider.generateAccessToken(authResult),
                refresh_token,
                userRepository.findByEmail(authResult.getName()).getId().toString()
        );

        redisTemplate.opsForValue().set(
                // Redis 데이터베이스에 저장할 키(key)
                authResult.getName(),
                // value
                refresh_token,
                // Redis에 저장될 기간
                Long.parseLong(env.getProperty("refresh_token.expiration_time")),
                // 기간의 단위를 마이크로 초로 지정
                TimeUnit.MICROSECONDS
        );
        
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON);
        response.getWriter().write(objectMapper.writeValueAsString(tokenWithUserIdDto));
    }
}
