package com.hex.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthFilter jwtAuthFilter) {
        return builder.routes()

                // ========================
                // User Service (JWT protected)
                // ========================
                .route("user-service", r -> r.path("/api/users/**", "/api/auth/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("http://localhost:8081"))

                // ========================
                // Ticket Service (JWT protected)
                // ========================
                .route("ticket-service", r -> r.path("/api/tickets/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("http://localhost:8082"))

                // ========================
                // Swagger/OpenAPI: user-service (NO JWT filter)
                // ========================
                .route("user-swagger-docs", r -> r.path(
                                "/v3/api-docs/user-service",
                                "/v3/api-docs/user-service/**",
                                "/swagger-ui/user-service/**")
                        .filters(f -> f
                                // forward /v3/api-docs/user-service/... → /v3/api-docs/...
                                .rewritePath("/v3/api-docs/user-service/(?<segment>.*)", "/v3/api-docs/${segment}")
                                .rewritePath("/v3/api-docs/user-service", "/v3/api-docs")
                        )
                        .uri("http://localhost:8081"))

                // ========================
                // Swagger/OpenAPI: ticket-service (NO JWT filter)
                // ========================
                .route("ticket-swagger-docs", r -> r.path(
                                "/v3/api-docs/ticket-service",
                                "/v3/api-docs/ticket-service/**",
                                "/swagger-ui/ticket-service/**")
                        .filters(f -> f
                                // forward /v3/api-docs/ticket-service/... → /v3/api-docs/...
                                .rewritePath("/v3/api-docs/ticket-service/(?<segment>.*)", "/v3/api-docs/${segment}")
                                .rewritePath("/v3/api-docs/ticket-service", "/v3/api-docs")
                        )
                        .uri("http://localhost:8082"))

                .build();
    }
}
