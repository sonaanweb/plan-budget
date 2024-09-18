package com.project.planb.controller;

import com.project.planb.dto.req.MemberJoinReqDto;
import com.project.planb.dto.req.MemberLoginReqDto;
import com.project.planb.dto.res.TokenResDto;
import com.project.planb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinReqDto reqDto) {
        authService.join(reqDto);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResDto> login(@RequestBody MemberLoginReqDto reqDto) {
        TokenResDto tokenResDto = authService.login(reqDto);
        // JWT 토큰을 응답 헤더에 추가
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenResDto.accessToken()) // 액세스 토큰 추가
                .body(tokenResDto);
    }
}
