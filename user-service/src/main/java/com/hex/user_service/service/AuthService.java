package com.hex.user_service.service;

import com.hex.user_service.dto.AuthDtos;
import com.hex.user_service.entity.User;
import com.hex.user_service.repository.UserRepository;
import com.hex.user_service.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwt;


    public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest req) {
        if (userRepository.findByUsername(req.username()).isPresent())
            throw new RuntimeException("Username already exists");
        if (userRepository.findByEmail((req.email())).isPresent())
            throw new RuntimeException("Email already exists");


        var user = User.builder()
                .username((req.username()))
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(req.role())
                .build();
        userRepository.save(user);
        String token = jwt.generateToken(user.getUsername(), Map.of("role", user.getRole()));

        return new AuthDtos.AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        var user = userRepository.findByUsername(req.username()).orElseThrow();
        String token = jwt.generateToken(user.getUsername(), Map.of("role", "ROLE_" + user.getRole()));
        return new AuthDtos.AuthResponse(token, user.getUsername(), user.getRole().name());

    }

}
