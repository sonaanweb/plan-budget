package com.project.planb.common.security.dto;

public record TokenResDto(
        // 성공 시 클라이언트 반환 dto
        String accessToken,
        String refreshToken
) {
}
