package com.hex.api_gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SwaggerConfig {


    private final SwaggerUiConfigParameters swaggerUiConfigParameters;

    public SwaggerConfig(SwaggerUiConfigParameters swaggerUiConfigParameters) {
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
    }

    @PostConstruct
    public void configureSwaggerGroups() {
        swaggerUiConfigParameters.addGroup("user-service");
        swaggerUiConfigParameters.addGroup("ticket-service");
    }

    @Bean
    public OpenAPI caseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway")
                        .description("Gateway documentation for User and Ticket services")
                        .version("v1"));
    }





}
