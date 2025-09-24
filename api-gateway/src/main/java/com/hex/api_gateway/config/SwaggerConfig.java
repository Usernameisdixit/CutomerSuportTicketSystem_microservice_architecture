package com.hex.api_gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI caseOpenAPI() {
        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement()
//                        .addList("bearerAuth")).components(new Components()
//                        .addSecuritySchemes(
//                                "bearerAuth", new SecurityScheme()
//                                        //.name("Authorization")
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .bearerFormat("JWT")
//                                        //.in(SecurityScheme.In.HEADER)
//                                        .scheme("bearer")
//                        )
//                )
                .info(new Info()
                        .title("API Gateway")
                        .description("Gateway documentation for User and Ticket services")
                        .version("v1")
                );
    }
}