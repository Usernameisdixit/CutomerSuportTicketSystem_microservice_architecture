package com.hex.user_service.security;

import com.hex.user_service.service.CustomUserDetailsService;
import com.hex.user_service.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sound.midi.SysexMessage;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService uds;


    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService uds) {
        this.jwtService = jwtService;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        final String header = req.getHeader("Authorization");
        System.out.print("Header" + header);
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.substring(7);
        String username = null;

        try {
            username = jwtService.extractUsername(token);
            System.out.print("Username1" + username);
        } catch (Exception e) {
            System.out.println("Invalid Token" + e.getMessage());
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = uds.loadUserByUsername(username);
            if (jwtService.isValid(token, userDetails.getUsername())) {
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                System.out.print("context1" + SecurityContextHolder.getContext().getAuthentication());

                System.out.print("Auth Value" + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.print("Username2" + username);
                System.out.print("Principal Class" + auth.getPrincipal().getClass());
                System.out.print("Principal Value" + auth.getClass());


            }
        }
        chain.doFilter(req, res);


    }
}



