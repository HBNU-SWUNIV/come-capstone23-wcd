package com.wcd.scheduleservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("Schedule-Service Open API with Swagger")
                        .description("일정 서비스 API 명세")
                        .version("1.0.0"));
    }
}

