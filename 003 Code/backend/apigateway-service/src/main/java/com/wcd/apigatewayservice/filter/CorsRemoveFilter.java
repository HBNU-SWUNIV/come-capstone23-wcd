package com.wcd.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CorsRemoveFilter extends AbstractGatewayFilterFactory<CorsRemoveFilter.Config> {

    public CorsRemoveFilter() {
        super(Config.class);
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // ServerHttpRequest 및 ServerHttpResponse 객체 가져오기
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // HttpHeaders 가져오기
            HttpHeaders headers = response.getHeaders();

            log.info("CorsRemoveFilter => Original headers: {}", headers);

            // 특정 헤더 삭제
            headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
            headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS);

            log.info("CorsRemoveFilter => Headers after removal: {}", headers);

            // 수정된 헤더로 응답 보내기
            return chain.filter(exchange.mutate().response(response).build());
        };
    }
}