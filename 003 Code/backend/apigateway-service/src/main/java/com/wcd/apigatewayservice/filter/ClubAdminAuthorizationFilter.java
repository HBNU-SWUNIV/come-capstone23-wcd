package com.wcd.apigatewayservice.filter;

import com.wcd.apigatewayservice.client.ClubServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClubAdminAuthorizationFilter extends AbstractGatewayFilterFactory<ClubAdminAuthorizationFilter.Config> {
    ClubServiceClient clubServiceClient;

    public ClubAdminAuthorizationFilter(@Lazy ClubServiceClient clubServiceClient) {
        super(Config.class);
        this.clubServiceClient = clubServiceClient;
    }

    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
