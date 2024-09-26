package com.project.planb.dto.res;

import java.util.List;

public record StaticsDto(
        // 통계용 DTO weekly, monthly
        // weekly, monthly
        int lastAmount, // todo: Integer & int 값타입 통일 및 정리
        int currentAmount, // 현재 지출
        double increaseRate, // 총 지출 증가 비율
        List<CategoryResDto> categories
) {
    // 카테고리별 응답 dto
    public record CategoryResDto(
            String categoryName,
            int lastAmount,
            int currentAmount,
            double increaseRate
    ){

    }
}
