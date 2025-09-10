package com.hex.user_service.controller;


import com.hex.user_service.dto.AuthDtos;
import com.hex.user_service.repository.UserRepository;
import com.hex.user_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;



    @PostMapping("/register")
    public ResponseEntity<AuthDtos.AuthResponse> register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.AuthResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthDtos.MeResponse> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("satyam");
        System.out.println(auth.getName());


        var user = userRepository.findByUsername(auth.getName()).orElseThrow();
        System.out.println("satyam");
        return ResponseEntity.ok(new AuthDtos.MeResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getRole().name()));
    }





}
