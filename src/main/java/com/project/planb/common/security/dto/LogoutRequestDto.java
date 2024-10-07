package com.project.planb.common.security.dto;

public record LogoutRequestDto(String accessToken, String refreshToken) {
}
