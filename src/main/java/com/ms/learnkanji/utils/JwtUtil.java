package com.ms.learnkanji.utils;

import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public JwtUtil(UserRepository userRepository,
                   HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }

    private String extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", String.class);
    }

    private Date extractDateExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        return extractDateExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUserIdFromToken() {
        if (httpServletRequest.getHeader("Authorization") != null) {
            String token = httpServletRequest.getHeader("Authorization").substring(7);
            return extractUserId(token);
        }
        return null;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        assert user != null;
        claims.put("id", user.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
