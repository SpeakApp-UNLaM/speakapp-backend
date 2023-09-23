package com.guba.spring.speakappbackend.security.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
@Getter
@Setter
@ConfigurationProperties("speak-backend.jwt")
public class JwtService {

    private Duration refreshExpirationToken;
    private Duration expirationToken;
    private String secretKey;

    public String getUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public Date getDateExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public boolean isJwtValid(String jwt, UserDetails u) {
        boolean isJwtExpirated = getDateExpiration(jwt).before(new Date());
        boolean isUserValid = u.getUsername().equals(getUsername(jwt));
        return isUserValid && !isJwtExpirated;
    }

    public String generateJwt(UserDetails u) {
        return Jwts
                .builder()
                //.setClaims(Map.of("a", "HOLA"))
                .setSubject(u.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpirationToken().toMillis()))
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();

    }

    private <T> T extractClaim(String jwt, Function<Claims, T> resolver) {
        final Claims claims = getAllClaims(jwt);
        return resolver.apply(claims);
    }

    private Claims getAllClaims(String jwt) {
        return Jwts
                .parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

}
