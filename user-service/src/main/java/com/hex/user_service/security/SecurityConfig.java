package com.hex.user_service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hex.user_service.dto.ApiError;
import com.hex.user_service.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;


    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                       // .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
// invalid access handle
                .exceptionHandling(ex->ex.accessDeniedHandler
                        ((request, response, accessDeniedException)
                                -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");

                            ApiError error=new ApiError("Access Denied",
                                    request.getRequestURI(),
                                    HttpStatus.FORBIDDEN.value(),
                                    LocalDateTime.now()
                            );
                            ObjectMapper mapper=new ObjectMapper();
                            mapper.registerModule(new JavaTimeModule());
                            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                            response.getWriter().write(mapper.writeValueAsString(error));

                        })

//invalid login handle
                        .authenticationEntryPoint(((request, response, authException) ->
                        {
                            ApiError error=new ApiError("Invalid username or password",
                                    request.getRequestURI(),
                                    HttpStatus.UNAUTHORIZED.value(),
                                    LocalDateTime.now()
                            );

                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            ObjectMapper mapper=new ObjectMapper();
                            mapper.registerModule(new JavaTimeModule());
                            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                            response.getWriter().write(mapper.writeValueAsString(error));
                        }))
                )


                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }


}
