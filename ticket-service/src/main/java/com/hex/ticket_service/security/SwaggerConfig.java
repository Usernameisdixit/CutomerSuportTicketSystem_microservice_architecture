package com.hex.ticket_service.security;

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
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")).components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth", new SecurityScheme()
                                        //.name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat("JWT")
                                        //.in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                        )
                )
                .info(new Info()
                        .title("Customer Support Ticketing System")
                        .description("API Documentation")
                        .version("v1")
                );
    }
}