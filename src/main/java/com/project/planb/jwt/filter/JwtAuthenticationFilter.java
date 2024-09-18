package com.project.planb.jwt.filter;

import com.project.planb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 토큰을 가져온다
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer" 부분을 제외한 토큰 추출
            String token = authorizationHeader.substring(7);

            // 토큰의 유효성 검사
            if (authService.validateToken(token)) {
                // 유효한 경우 계정 정보를 가져온다.
                String account = authService.getAccountFromToken(token);

                // 인증된 사용자 정보 설정
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(account, null, new ArrayList<>());
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
