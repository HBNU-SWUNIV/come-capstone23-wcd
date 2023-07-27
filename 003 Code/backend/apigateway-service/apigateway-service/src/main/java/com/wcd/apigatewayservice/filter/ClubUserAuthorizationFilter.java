package com.wcd.apigatewayservice.filter;

import com.wcd.apigatewayservice.client.ClubServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;

import java.util.Map;

/*
    Custom Filter는 AbstractGatewayFilterFactory를 상속 받아야 한다.
    Configuration 정보가 있다면 자신의 클래스 안에서 Config라는 내부클래스를 매개변수로 등록한다.
 */
@Component
@Slf4j
public class ClubUserAuthorizationFilter extends AbstractGatewayFilterFactory<ClubUserAuthorizationFilter.Config> {
    ClubServiceClient clubServiceClient;

    public ClubUserAuthorizationFilter(@Lazy ClubServiceClient clubServiceClient) {
        super(Config.class);
        this.clubServiceClient = clubServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // Getting userId from header
            Long userId = Long.parseLong(request.getHeaders().getFirst("user-id"));

            // Getting clubId from path variable
            Map<String, Long> pathVariables = exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            Long clubId = null;
            if (pathVariables != null) {
                clubId = pathVariables.get("club-id");
            }

            // Now use the userId and clubId to call the clubServiceClient
            boolean isUserMemberOfClub = clubServiceClient.isUserMemberOfClub(clubId, userId);

            if (!isUserMemberOfClub) {
                // if user is not in the club, you can return a specific HTTP status or message
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return response.setComplete();
            }

            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
