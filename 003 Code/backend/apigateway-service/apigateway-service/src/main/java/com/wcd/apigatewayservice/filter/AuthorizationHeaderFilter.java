package com.wcd.apigatewayservice.filter;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;
    RedisTemplate<String, String> redisTemplate;

    public AuthorizationHeaderFilter(Environment env, RedisTemplate<String, String> redisTemplate) {
        super(Config.class);
        this.env = env;
        this.redisTemplate = redisTemplate;
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // HTTP 요청의 Authorization 헤더가 있는지 확인
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            // HTTP 요청 헤더에서 Authorization 필드에 해당하는 값을 가져옴
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);;
            // authorizationHeader 문자열에 Bearer 문자열을 제거
            String jwt = authorizationHeader.replace("Bearer ", "");

            // JWT 토큰의 서명을 검증하고, 만료 시간을 확인하여 JWT 토큰이 유효한지 검증
            if (jwt != null && isJwtValid(jwt)) {
                log.info(jwt);
                // Redis에 해당 accessToken logout 여부를 확인
                String isLogout = (String) redisTemplate.opsForValue().get(jwt);

                // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
                if (!ObjectUtils.isEmpty(isLogout)) {

                    return onError(exchange, "Please Login", HttpStatus.UNAUTHORIZED);
                }

            }
            else {
                log.info("JWT token is not valid");
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);

        }));
    }

    // jwt 문자열을 파싱하여 JWT 토큰이 유효한지 검증
    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;
        String subject = null;

        try {
            /*
                JWT 토큰 서명에 사용될 비밀 키 생성. 이때, 비밀 키는 애플리케이션에서 미리 설정한 token.secret 값으로부터 생성되며,
                hmacShaKeyFor 메서드는 HMAC-SHA 알고리즘을 사용하여 비밀 키를 생성
            */
            Key secretKey = Keys.hmacShaKeyFor(env.getProperty("access_token.secret").getBytes(StandardCharsets.UTF_8));

            // JWT 토큰을 파싱하기 위한 빌더 객체 생성 및 토큰에 사용될 서명 키 설정
            JwtParserBuilder jwtParserBuilder = Jwts.parserBuilder().setSigningKey(secretKey);
            subject = jwtParserBuilder
                    .build()
                    // 파싱 대상 JWT 토큰을 Jws(JWT Signature를 포함하는 객체) 객체로 파싱
                    .parseClaimsJws(jwt)
                    // 파싱된 JWT 내용을 가져옴
                    .getBody()
                    // JWT의 subject 값을 가져옴
                    .getSubject();
        } catch (Exception ex) {
            log.error("jwtParser = {}", ex.getMessage());
            returnValue = false;
        }

        return returnValue;
    }

    /*
        - ServerWebExchange: Spring WebFlux에서 사용되는 HTTP 요청 및 응답 객체
        - Mono -> WebFlux라고 해서 Spring 5 부터 추가된 기능
        - 기존에 동기화 방식의 서버가 아니라 비동기 방식의 서버를 지원할 때 단일값을 전달할 때 사용
    */
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);

        return response.setComplete();
    }
}
