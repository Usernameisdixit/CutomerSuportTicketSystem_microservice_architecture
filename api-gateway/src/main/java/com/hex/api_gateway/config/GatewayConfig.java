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
        return builder.routes()
                .route("user-service",r->
                         r.path("/api/users/**","/api/auth/**")
                        .filters(f->f.filter(jwtAuthFilter))
                        .uri("http://localhost:8081"))
                .route("ticket-service",r->
                         r.path("/api/tickets/**")
                                 .filters(f->f.filter((jwtAuthFilter)))
                                 .uri("http://localhost:8082"))
//                .route("swagger",r->
//                        r.path("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html")
//                                .uri("http://localhost:8083"))
                .route("user-swagger",r->
                        r.path("/user/v3/api-docs","/user/swagger-ui/**","/user/swagger-ui.html")
                        .uri("http://localhost:8081"))
                .route("ticket-swagger",r->
                        r.path("/ticket/v3/api-docs","/ticket/swagger-ui/**","/ticket/swagger-ui.html")
                                .uri("http://localhost:8082"))

                .build();
    }
}
