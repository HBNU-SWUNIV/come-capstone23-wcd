package com.wcd.userservice.security.jwt;

import com.wcd.userservice.exception.CustomException;
import com.wcd.userservice.service.MyUserDetailsService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private Environment env;
    private MyUserDetailsService myUserDetailsService;

    public JwtTokenProvider(Environment env, MyUserDetailsService myUserDetailsService) {
        this.env = env;
        this.myUserDetailsService = myUserDetailsService;
    }

    public String generateAccessToken(Authentication authentication) {
        Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

        String access_token = Jwts.builder()
                // JWT 토큰의 subject를 설정
                .setSubject(authentication.getName())
                // JWT 토큰의 만료 시간 설정(현재 시간 + token.expiration_time 값)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("access_token.expiration_time"))))
                // JWT 토큰에 서명 추가
                .signWith(secretKey, SignatureAlgorithm.HS512)
                // JWT 토큰을 문자열로 변환
                .compact();

        return access_token;
    }

    public String generateRefreshToken(Authentication authentication) {
        Key secretKey = Keys.hmacShaKeyFor(env.getProperty("refresh_token.secret").getBytes(StandardCharsets.UTF_8));

        String refresh_token = Jwts.builder()
                // JWT 토큰의 subject를 설정
                .setSubject(authentication.getName())
                // JWT 토큰의 만료 시간 설정(현재 시간 + token.expiration_time 값)
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("refresh_token.expiration_time"))))
                // JWT 토큰에 서명 추가
                .signWith(secretKey, SignatureAlgorithm.HS512)
                // JWT 토큰을 문자열로 변환
                .compact();

        return refresh_token;
    }

    /**
     * Access 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
     * @param access_token
     * @return
     */
    public Authentication getAuthenticationByAccessToken(String access_token) {
        Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);

        String subject = jwtParserBuilder
                .build()
                // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                .parseClaimsJws(access_token)
                // 파싱된 JWT 내용을 가져옴
                .getBody()
                // JWT의 subject 값을 가져옴
                .getSubject();

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(subject);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Refresh 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
     * @param refresh_token
     * @return
     */
    public Authentication getAuthenticationByRefreshToken(String refresh_token) {
        Key secretKey = Keys.hmacShaKeyFor(env.getProperty("refresh_token.secret").getBytes(StandardCharsets.UTF_8));

        JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);


        String subject = jwtParserBuilder
                .build()
                // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                .parseClaimsJws(refresh_token)
                // 파싱된 JWT 내용을 가져옴
                .getBody()
                // JWT의 subject 값을 가져옴
                .getSubject();

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(subject);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * http 헤더로부터 bearer 토큰을 가져옴.
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Access 토큰을 검증
     * @param token
     * @return
     */
    public boolean validateAccessToken(String access_token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

            // JWT 토큰을 파싱하기 위한 빌더 객체 생성 및 토큰에 사용될 서명 키 설정
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
            jwtParserBuilder
                    .build()
                    // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                    .parseClaimsJws(access_token);

            return true;
        } catch (JwtException e) {
            // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
            throw new CustomException("Error on Access Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Refresh 토큰을 검증
     * @param token
     * @return
     */
    public boolean validateRefreshToken(String refresh_token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(env.getProperty("refresh_token.secret").getBytes(StandardCharsets.UTF_8));

            // JWT 토큰을 파싱하기 위한 빌더 객체 생성 및 토큰에 사용될 서명 키 설정
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
            jwtParserBuilder
                    .build()
                    // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                    .parseClaimsJws(refresh_token);
            return true;
        } catch (JwtException e) {
            // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
            throw new CustomException("Error on Refresh Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Long getAccessTokenExpiration(String access_token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

            // JWT 토큰을 파싱하기 위한 빌더 객체 생성 및 토큰에 사용될 서명 키 설정
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
            Long expirationTime = jwtParserBuilder
                    .build()
                    // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                    .parseClaimsJws(access_token)
                    .getBody()
                    .getExpiration()
                    .getTime();
            Long currentTime = System.currentTimeMillis();
            Long remainingTime = expirationTime - currentTime;
            return remainingTime;
        } catch (JwtException e) {
            // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
            throw new CustomException("Error on Refresh Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
