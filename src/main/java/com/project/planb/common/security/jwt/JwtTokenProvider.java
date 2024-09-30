package com.project.planb.common.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secret) {
        // secretKey 생성
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 토큰 유효 시간
    private final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30; // 30분
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

    public String createAccessToken(String account) {
        return createToken(account, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(String account) {
        return createToken(account, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String createToken(String account, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(account)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 계정 정보 추출
    public String getAccountFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // 토큰의 모든 Claims 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true; // 유효한 토큰
        } catch (Exception e) {
            log.error("토큰 유효성 검사 실패: {}", e.getMessage());
            return false; // 유효하지 않은 토큰
        }
    }
}
