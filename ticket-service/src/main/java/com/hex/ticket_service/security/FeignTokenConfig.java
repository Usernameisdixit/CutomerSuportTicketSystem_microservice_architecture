package com.hex.ticket_service.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@Configuration
public class FeignTokenConfig {
    @Bean
    public RequestInterceptor jwtRequestInterceptor() {
        return (RequestTemplate template) -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("Auth value in FeignTokenConfig: {}",auth);
            if (auth != null && auth.getCredentials() != null) {
                String token = auth.getCredentials().toString();
                log.info("Token in FeignTokenConfig: {}",token);
                template.header("Authorization", "Bearer " + token);

            }


        };
    }
}