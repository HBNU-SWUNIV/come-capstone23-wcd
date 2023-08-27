package com.wcd.chattingservice.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.security.Key;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private Environment env;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(accessor.getCommand() == StompCommand.CONNECT) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            if (!validateToken(jwtToken)) {
                throw new RuntimeException("Invalid JWT token");
            }
        }
        return message;
    }

    public boolean validateToken(String access_token) {
        try {
            Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(access_token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
