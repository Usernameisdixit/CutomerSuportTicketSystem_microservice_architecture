package com.hex.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,JwtAuthFilter jwtAuthFilter)
    {
        return builder.routes().route("user-service",r->
                r.path("/api/auth/**","/api/users/**")
                        .filters(f->f.filter(jwtAuthFilter))
                        .uri("http://localhost:8081"))
                        .route("ticket-service",r->
                                r.path("/api/tickets/**")
                                        .filters(f->f.filter((jwtAuthFilter)))
                                        .uri("http://localhost:8082"))
                .build();
    }
}
