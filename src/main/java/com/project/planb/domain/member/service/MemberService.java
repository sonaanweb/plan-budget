package com.project.planb.domain.member.service;

import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.common.security.dto.LogoutRequestDto;
import com.project.planb.common.security.dto.RefreshToken;
import com.project.planb.common.security.dto.TokenRequestDto;
import com.project.planb.common.security.dto.TokenResDto;
import com.project.planb.common.security.jwt.JwtTokenProvider;
import com.project.planb.common.security.repository.RefreshTokenRepository;
import com.project.planb.domain.member.dto.MemberJoinReqDto;
import com.project.planb.domain.member.dto.MemberLoginReqDto;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // 회원가입
    @Transactional
    public void join(MemberJoinReqDto reqDto) {
        if (memberRepository.existsByAccount(reqDto.account())) {
            // 중복회원 검증
            throw new CustomException(ErrorCode.DUPLICATED_ACCOUNT);
        }
        Member member = Member.builder()
                .account(reqDto.account())
                .password(passwordEncoder.encode(reqDto.password()))
                .notificationEnabled(reqDto.notificationEnabled())
                .build();
        memberRepository.save(member);
    }

    // 로그인
    public TokenResDto login(MemberLoginReqDto reqDto) {
        Member member = memberRepository.findByAccount(reqDto.account())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(reqDto.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }

        // 로그인 시 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getAccount());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getAccount());

        // 리프레시 토큰을 Redis에 저장
        RefreshToken refreshTokenEntity = new RefreshToken(refreshToken, String.valueOf(member.getId()), member.getAccount()); // account 추가
        refreshTokenRepository.save(refreshTokenEntity);

        return new TokenResDto(accessToken, refreshToken);
    }

    // 리프레시 토큰 처리 및 새로운 액세스 토큰 발급
    public TokenResDto reissueToken(TokenRequestDto requestDto) {
        String refreshToken = requestDto.refreshToken();

        // 1. Redis에서 refreshToken 확인
        RefreshToken savedToken = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        // 2. 저장된 리프레시 토큰이 유효하다면 새로운 액세스 토큰 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(savedToken.getAccount());

        // 3. 새로운 액세스 토큰 반환
        return new TokenResDto(newAccessToken, savedToken.getRefreshToken());
    }

    // 로그아웃
    @Transactional
    public void logout(LogoutRequestDto requestDto) {

        if (!jwtTokenProvider.validateToken(requestDto.accessToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        if (!jwtTokenProvider.validateToken(requestDto.refreshToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        // Refresh Token 삭제
        String refreshToken = requestDto.refreshToken();
        refreshTokenRepository.deleteById(refreshToken);

        // 액세스 토큰을 블랙리스트에 추가
        redisTemplate.opsForValue().set(requestDto.accessToken(), "invalid", jwtTokenProvider.getTokenBlacklistTTL(), TimeUnit.SECONDS);
    }

    // account를 통해 Member 엔티티를 조회
    public Optional<Member> findByAccount(String account) {
        return memberRepository.findByAccount(account);
    }
}
