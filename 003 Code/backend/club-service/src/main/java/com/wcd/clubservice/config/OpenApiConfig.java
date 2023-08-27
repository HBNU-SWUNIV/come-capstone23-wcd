package com.wcd.clubservice.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("club-service")
                .description("club-service API");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}