package com.project.planb.common.security.jwt.filter;

import com.project.planb.common.security.details.PrincipalDetails;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 토큰을 가져온다
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer" 부분을 제외한 토큰 추출
            String token = authorizationHeader.substring(7);

            // 토큰의 유효성 검사
            if (memberService.validateToken(token)) {
                // 유효한 경우 계정 정보를 가져온다.
                String account = memberService.getAccountFromToken(token);

                // Member 엔티티 조회
                Member member = memberService.findByAccount(account)
                        .orElseThrow(() -> new RuntimeException("Member not found: " + account));

                // PrincipalDetails 객체 생성
                PrincipalDetails principalDetails = new PrincipalDetails(member);

                // 인증된 사용자 정보 설정
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // 유효하지 않은 토큰인 경우
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return; // 필터 체인 종료
            }
        }
        filterChain.doFilter(request, response);
    }
}
