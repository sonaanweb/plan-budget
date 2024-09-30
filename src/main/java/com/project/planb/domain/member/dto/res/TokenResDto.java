package com.project.planb.domain.member.dto.res;

public record TokenResDto(
        // 성공 시 클라이언트 반환 dto
        String accessToken,
        String refreshToken
) {
}
