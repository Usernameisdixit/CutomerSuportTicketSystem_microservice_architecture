package com.hex.ticket_service.security;

//import com.hex.customerSupportApp.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sound.midi.SysexMessage;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    //private final CustomUserDetailsService uds;


//    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService uds) {
//        this.jwtService = jwtService;
//        this.uds = uds;
//    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        String path = request.getServletPath();
//        logger.info("Path value inside shouldNotFilter: {}");
//        logger.info(path);
//
//        return path.contains("/v3/api-docs")
//                || path.contains("/swagger-ui")
//                || path.equals("/swagger-ui.html")
//                || path.contains("/swagger-resources")
//                || path.contains("/webjars")
//                || path.startsWith("/api/tickets");
//    }


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
            List<String> roles=jwtService.extractRoles(token);
            System.out.print("Username1" + username);
            System.out.print("Roles" + roles);

//        } catch (Exception e) {
//            System.out.println("Invalid Token" + e.getMessage());
//        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //UserDetails userDetails = uds.loadUserByUsername(username);
           // if (jwtService.isValid(token, userDetails.getUsername())) {
            List<GrantedAuthority> authorities=roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());


               // var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            var auth = new UsernamePasswordAuthenticationToken(username,token,authorities);

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                System.out.print("context1" + SecurityContextHolder.getContext().getAuthentication());

                System.out.print("Auth Value in JWT auth filter" + auth);
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.print("Username2" + username);
                System.out.print("Principal Class" + auth.getPrincipal().getClass());
                System.out.print("Principal Value" + auth.getClass());
                System.out.print("Roles"+authorities);


            }
        } catch (Exception e) {
            System.out.println("Invalid Token" + e.getMessage());
        }
        chain.doFilter(req, res);


    }
}



