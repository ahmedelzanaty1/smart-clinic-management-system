package com.springlearn.smartclinicapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenService {

    private static final String SECRET_KEY = "smartclinicprojectsecretkey1234567890"; // لازم يكون أكثر من 32 حرف

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 ساعة

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // إنشاء التوكن
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // استخراج الـ username من التوكن
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // التحقق من صلاحية التوكن
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // تحقق من انتهاء الصلاحية
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // استخراج تاريخ الانتهاء
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // استخراج أي claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
