package com.wcd.userservice.service.auth;

import com.wcd.userservice.entity.Users;
import com.wcd.userservice.file.FileStore;
import com.wcd.userservice.dto.user.request.RequestSignUp;
import com.wcd.userservice.exception.CustomException;
import com.wcd.userservice.repository.UserRepository;
import com.wcd.userservice.security.jwt.JwtTokenProvider;
import com.wcd.userservice.security.jwt.dto.RegenerateTokenDto;
import com.wcd.userservice.security.jwt.dto.TokenDto;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final Environment env;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    private final JavaMailSender javaMailSender;

    private final FileStore fileStore;

    // 토큰 재발급
    @Transactional
    @Override
    public TokenDto regenerateToken(RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefresh_token();

        // Refresh Token 검증
        if(!jwtTokenProvider.validateRefreshToken(refresh_token)) {
            throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
        }

        // Access Token에서 userId 가져오기
        Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(refresh_token);

        // Redis에 저장된 Refresh Token 값 가져오기
        String refreshToken = redisTemplate.opsForValue().get("refreshToken:" + authentication.getName());
        if(!refreshToken.equals(refresh_token)) {
            throw new CustomException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
        }

        // 토큰 재발행
        String new_refresh_token = jwtTokenProvider.generateRefreshToken(authentication);

        TokenDto tokenDto = new TokenDto(
                jwtTokenProvider.generateAccessToken(authentication),
                new_refresh_token
        );

        redisTemplate.opsForValue().set(
                "refreshToken:" + authentication.getName(),
                new_refresh_token,
                Long.parseLong(env.getProperty("refresh_token.expiration_time")),
                TimeUnit.MICROSECONDS
        );

        return tokenDto;
    }

    @Transactional
    @Override
    public Long signUp(RequestSignUp requestSignUp) {

        // Check if user with the provided loginId already exists
        if (userRepository.existsByEmail(requestSignUp.getEmail())) {
            throw new IllegalArgumentException("A user with this loginId already exists.");
        } else if (userRepository.existsByPhoneNumber(requestSignUp.getPhoneNumber())) {
            throw new IllegalArgumentException("A user with this phone number already exists.");
        }

        Users user = requestSignUp.toEntity(passwordEncoder.encode(requestSignUp.getPassword()));

        return userRepository.save(user).getId();
    }


    @Override
    public void logout(TokenDto tokenDto) {
        // 로그아웃 하고 싶은 토큰이 유효한 지 검증
        if(!jwtTokenProvider.validateAccessToken(tokenDto.getAccess_token())) {
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        // Access Token에서 userId를 가져옴
        Authentication authentication = jwtTokenProvider.getAuthenticationByAccessToken(tokenDto.getAccess_token());

        // Redis에서 해당 userId로 저장된 Refresh Token이 있는지 여부 확인
        if ( redisTemplate.opsForValue().get(authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete(authentication.getName());
        }

        // 해당 Access Token 유효시간을 가져와 BlackList에 저장
        Long expiration = jwtTokenProvider.getAccessTokenExpiration(tokenDto.getAccess_token());
        redisTemplate.opsForValue().set(tokenDto.getAccess_token(), "logout", expiration * 1000, TimeUnit.MICROSECONDS);
    }

    @Override
    public void sendAuthenticationEmail(String email){
        if(userRepository.existsByEmail(email)) {
            throw new CustomException("이메일 주소가 이미 사용중입니다.", HttpStatus.CONFLICT);
        }

        String code = generateAuthenticationCode();
        storeCodeInRedis(email, code);
        sendEmail(email, code);
    }

    private String generateAuthenticationCode() {
        return String.valueOf((int)((Math.random() * 90000) + 100000));
    }

    private void storeCodeInRedis(String email, String code) {
        String key = "emailCode:" + email;
        redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
    }

    private void sendEmail(String email, String code) throws CustomException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            message.setFrom(new InternetAddress(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.personal")));
            email = "vomw99@naver.com";
            message.setRecipients(RecipientType.TO, email);
            message.setSubject("이메일 인증");
            String body = "<h3>요청하신 인증 번호입니다.</h3>"
                    + "<h1>" + code + "</h1>"
                    + "<h3>감사합니다.</h3>";
            message.setText(body, "UTF-8", "html");

            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error("Error sending authentication email to {}", email, e);
            throw new CustomException("이메일 전송 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyAuthenticationCode(String email, String inputCode) {
        String key = "emailCode:" + email;
        String code = redisTemplate.opsForValue().get(key);

        if (code == null || code.isEmpty()) {
            return false;
        } else if (code.equals(inputCode)) {
            redisTemplate.delete(key);
            return true;
        } else {
            return false;
        }
    }
}
