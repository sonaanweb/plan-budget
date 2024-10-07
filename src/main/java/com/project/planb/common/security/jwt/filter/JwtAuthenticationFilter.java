package com.project.planb.common.security.jwt.filter;

import com.project.planb.common.security.details.PrincipalDetails;
import com.project.planb.common.security.jwt.JwtTokenProvider;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Header에서 토큰 가져오기
        String token = jwtTokenProvider.getTokenFromHeader(request);

        // 토큰이 존재하고 유효한 경우
        if (token != null && jwtTokenProvider.validateToken(token)) {

            if (redisTemplate.hasKey(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return; // 필터 체인 종료
            }

            // 계정 정보 가져오기
            String account = jwtTokenProvider.getAccountFromToken(token);

            // Member 엔티티 조회
            Member member = memberService.findByAccount(account)
                    .orElseThrow(() -> new RuntimeException("Filter Member not found: " + account));

            // PrincipalDetails 객체 생성
            PrincipalDetails principalDetails = new PrincipalDetails(member);

            // 인증된 사용자 정보 설정
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 유효하지 않은 토큰인 경우
            if (token != null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return; // 필터 체인 종료
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}