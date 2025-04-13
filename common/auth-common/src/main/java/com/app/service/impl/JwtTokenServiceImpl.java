package com.app.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.enums.UserRole;
import com.app.exception.auth.TokenException;
import com.app.model.UserAuth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl {
    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(UserAuth userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserAuth user) {
            if (user.getRoles() != null) {
                List<String> roleNames = user.getRoles().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList());
                claims.put("roles", roleNames);
            }

            claims.put("email", user.getEmail());
            claims.put("id", user.getId());
        }
        return createToken(claims, userDetails.getEmail(), jwtExpiration);
    }

    public UserAuth decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        var email = claims.get("email");
        var id = claims.get("id");
        var roles = claims.get("roles");

        var user = UserAuth.builder()
                .email(email.toString())
                .id(id.toString())
                .roles(((List<?>) roles).stream()
                        .map(Object::toString)
                        .map(UserRole::valueOf)
                        .collect(Collectors.toSet()))
                .build();

        return user;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw TokenException.expired();
        } catch (IllegalArgumentException e) {
            throw TokenException.invalid();
        } catch (MalformedJwtException e) {
            throw TokenException.malformed();
        } catch (Exception e) {
            return false;
        }
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}
