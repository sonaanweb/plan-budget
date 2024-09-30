package com.project.planb.domain.member.controller;

import com.project.planb.domain.member.dto.MemberJoinReqDto;
import com.project.planb.domain.member.dto.MemberLoginReqDto;
import com.project.planb.common.security.dto.TokenResDto;
import com.project.planb.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="사용자 API", description = "회원가입 및 로그인 관련 API 제공")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinReqDto reqDto) {
        memberService.join(reqDto);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResDto> login(@RequestBody MemberLoginReqDto reqDto) {
        TokenResDto tokenResDto = memberService.login(reqDto);
        // JWT 토큰을 응답 헤더에 추가
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenResDto.accessToken()) // 액세스 토큰 추가
                .body(tokenResDto);
    }
}
