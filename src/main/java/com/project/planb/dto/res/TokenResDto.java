package com.project.planb.dto.res;

public record TokenResDto(
        // 성공 시 클라이언트 반환 dto
        String accessToken,
        String refreshToken
) {
}
