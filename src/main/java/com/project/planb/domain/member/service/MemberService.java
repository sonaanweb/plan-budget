package com.project.planb.domain.member.service;

import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.common.security.dto.TokenResDto;
import com.project.planb.common.security.jwt.JwtTokenProvider;
import com.project.planb.domain.member.dto.MemberJoinReqDto;
import com.project.planb.domain.member.dto.MemberLoginReqDto;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public void join(MemberJoinReqDto reqDto) {
        if (memberRepository.existsByAccount(reqDto.account())) {
            // 중복회원 검증
            throw new CustomException(ErrorCode.DUPLICATED_ACCOUNT);
        }
        Member member = Member.builder()
                .account(reqDto.account())
                .password(passwordEncoder.encode(reqDto.password()))
                .build();
        memberRepository.save(member);
    }

    // 로그인
    // todo : DB에서 확인하고 존재하지 않는 회원일 시 예외처리 추가
    public TokenResDto login(MemberLoginReqDto reqDto) {
        Optional<Member> memberOpt = memberRepository.findByAccount(reqDto.account());

        // 회원이 존재하고 비밀번호가 일치하는지 확인
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();

            // 비밀번호 일치 여부 확인
            if (passwordEncoder.matches(reqDto.password(), member.getPassword())) {
                // 비밀번호가 일치할 경우 토큰 생성
                String accessToken = jwtTokenProvider.createAccessToken(member.getAccount());
                String refreshToken = jwtTokenProvider.createRefreshToken(member.getAccount());
                return new TokenResDto(accessToken, refreshToken);
            }
        }

        // 로그인 실패 시 예외 발생
        throw new CustomException(ErrorCode.LOGIN_FAILED);
    }

    // 리프레시 토큰 처리 및 새로운 액세스 토큰 발급
    public ResponseEntity<TokenResDto> refreshAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String account = jwtTokenProvider.getAccountFromToken(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(account);

        return ResponseEntity.ok(new TokenResDto(newAccessToken, refreshToken));
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public String getAccountFromToken(String token) {
        return jwtTokenProvider.getAccountFromToken(token);
    }

    // account를 통해 Member 엔티티를 조회
    public Optional<Member> findByAccount(String account) {
        return memberRepository.findByAccount(account);
    }
}
