package com.wcd.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/*
    Custom Filter는 AbstractGatewayFilterFactory를 상속 받아야 한다.
    Configuration 정보가 있다면 자신의 클래스 안에서 Config라는 내부클래스를 매개변수로 등록한다.
 */
@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() { super(Config.class); }

    @Override
    public GatewayFilter apply(Config config) {
        // exchange: GatewayFilter에서 제공하는 ServerWebExchange 객체 (Spring WebFlux에서 사용). HTTP 요청 및 응답을 나태냄
        // chain: 필터 체인을 나타내는 객체
        return (((exchange, chain) -> {
            // spring cloud gateway는 비동식 방식(Netty)이기 때문에 ServerHttpRequest라는 객체를 사용한다.(ServletRequest가 아님)
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
                log.info("Global Filter Start: request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Global Filter End: response code -> {}", response.getStatusCode());
                }
            }));
        }));
    }

    // Inner Class로써 설정과 관련되어 있는 작업을 전달하기 위한 Config 등록
    @Data
    public static class Config {
        private boolean preLogger;
        private boolean postLogger;
    }
}
