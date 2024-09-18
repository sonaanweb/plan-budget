package com.project.planb.dto.res;

public record CategoryResDto(
        // 카테고리 목록 반환 응답 dto
        Long id,
        String categoryName
) {
}
