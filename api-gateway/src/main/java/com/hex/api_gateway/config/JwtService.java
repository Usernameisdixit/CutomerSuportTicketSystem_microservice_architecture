package com.hex.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    private final Key key;
    private final long expirationMS;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMS

    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMS = expirationMS;
    }



//    public  String generateToken(String username, Map<String,Object> claims)
//    {
//        Date now =new Date();
//        Date exp=new Date(now.getTime()+expirationMS);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .addClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(exp)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }


    public String extractUsername(String token)
    {
        return parse(token).getBody().getSubject();
    }

    public List<String> extractRoles(String token)
    {
        Claims claims=extractAllClaims(token);
        return claims.get("roles",List.class);
    }


    private Claims extractAllClaims(String token)
    {
        return  Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean isValid(String token,String username)
    {
        var body=parse(token).getBody();
        return username.equals(body.getSubject()) && body.getExpiration().after(new Date());
    }

    private Jws<Claims> parse(String token)
    {
        return  Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
    }
}

