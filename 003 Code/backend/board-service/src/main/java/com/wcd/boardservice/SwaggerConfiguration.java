package com.wcd.boardservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("Board-Service Open API with Swagger")
                .description("게시판 서비스 API 명세")
                .version("1.0.0"));
    }
}
