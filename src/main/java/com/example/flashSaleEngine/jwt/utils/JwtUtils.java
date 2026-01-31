package com.example.flashSaleEngine.jwt.utils;

import ch.qos.logback.core.net.server.Client;
import com.example.flashSaleEngine.dto.AuthRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    private String KEY = "070df949d4329a55045e2b309b106ef6";


    public String generateToken(AuthRequest request){
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(request.getUsername())
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*100))
                .compact();
    }
    public String nameExtractor(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validToken(String token, UserDetails userDetails) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        boolean isExpired = claim.getExpiration().before(new Date(System.currentTimeMillis()));

        return userDetails.getUsername().equals(claim.getSubject()) && !isExpired;
    }
}
