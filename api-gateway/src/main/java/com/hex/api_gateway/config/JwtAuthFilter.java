package com.hex.api_gateway.config;

import lombok.AllArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class JwtAuthFilter implements GatewayFilter {

    private final JwtService jwtService;
    //private final CustomUserDetailsService uds;


//    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService uds) {
//        this.jwtService = jwtService;
//        this.uds = uds;
//    }




    @Override
    public reactor.core.publisher.Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       String path=exchange.getRequest().getURI().getPath();
        System.out.println("path in jwtAuthFilter in api-gateway"+path);
//       if(path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui") ) {
//           return chain.filter(exchange);
//       }
        final String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.print("Header in jwtAuthFilter " + header);
        if (header == null || !header.startsWith("Bearer")) {
            //chain.doFilter(req, res);
            return chain.filter(exchange);
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
            ServletServerHttpRequest mutated= (ServletServerHttpRequest) exchange.getRequest()
                    .mutate().header("X-User-Name",username)
                    .header("X-User-Roles",String.join(",",roles))
                    .build();

            return chain.filter(exchange.mutate().request((ServerHttpRequest) mutated).build());

//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                //UserDetails userDetails = uds.loadUserByUsername(username);
//                // if (jwtService.isValid(token, userDetails.getUsername())) {
//                List<GrantedAuthority> authorities=roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//                // var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                var auth = new UsernamePasswordAuthenticationToken(username,token,authorities);
//                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
//                System.out.print("context1" + SecurityContextHolder.getContext().getAuthentication());
//                System.out.print("Auth Value in JWT auth filter" + auth);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                System.out.print("Username2" + username);
//                System.out.print("Principal Class" + auth.getPrincipal().getClass());
//                System.out.print("Principal Value" + auth.getClass());
//                System.out.print("Roles"+authorities);
            } catch (Exception e) {
            //System.out.println("Invalid Token" + e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //chain.doFilter(req, res);


    }


}