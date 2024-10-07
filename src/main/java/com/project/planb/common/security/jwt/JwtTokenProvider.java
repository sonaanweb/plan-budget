package com.project.planb.common.security.jwt;

import com.project.planb.common.security.details.PrincipalDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    /**
     * JWT 기반의 인증 및 권한 부여 : 토큰 생성 / 반환 / 유효성 검증 / 사용자 정보 추출 등
     */

    // 파싱 암호키
    private SecretKey key;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-token-TTL}")
    private long accessTokenValidTime;

    @Value("${security.jwt.refresh-token-TTL}")
    private long refreshTokenValidTime;

    @Value("${security.jwt.prefix}")
    private String bearerPrefix;

    @Value("${security.jwt.token-blacklist-TTL}")
    private int tokenBlacklistTTL; // 블랙리스트 TTL 값 추가

    private final PrincipalDetailsService principalDetailsService;

    // 객체 생성 후 초기화 알고리즘 BASE64
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // accessToken 생성 메서드
    public String createAccessToken(String account) {
        return createToken(account, accessTokenValidTime); // 토큰 생성시 2번째 매개변수 활용(ttl)
    }

    // refreshToken 생성 메서드
    public String createRefreshToken(String account) {
        return createToken(account, refreshTokenValidTime);
    }

    /**
     * 토큰 생성 access, refresh
     */
    private String createToken(String account, long expirationTime) { // 위에서 각각 받아온 ttl - expirationTime
        Map<String, Object> claims = new HashMap<>();
        // 현재 시각과 만료 시각 계산
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(expirationTime);

        String token = Jwts.builder()
                .claims(claims)
                .subject(account)
                .issuedAt(Date.from(now)) // 현재 시각
                .expiration(Date.from(expiration)) // 만료 시각
                .signWith(key)
                .compact();

        log.info("토큰 생성 시각: {}", new Date());
        log.info("생성된 토큰: {}, 만료 시간: {}", token, Date.from(expiration));

        return token;
    }

    // 토큰에서 계정 정보 추출
    public String getAccountFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // 토큰의 모든 Claims 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Header에서 토큰 가져오는 메서드
    public String getTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith(bearerPrefix)) {
            return bearerToken.substring(bearerPrefix.length()).trim(); // "Bearer " 부분 제거
        }
        return null;
    }

    // 블랙리스트 TTL
    public int getTokenBlacklistTTL() {
        return tokenBlacklistTTL;
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
