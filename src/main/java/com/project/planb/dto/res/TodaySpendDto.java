package com.project.planb.dto.res;

import java.util.List;

public record ApiResponse<T>(
        String status,  // 응답 상태
        T data,         // 실제 데이터
        String message   // 메시지
) {}
public record TodaySpendDto(
        int totalSpentAmount,   // 오늘 총 지출 금액, 사용된 금액이므로 = `spent`로 설정
        int recommendedAmount,   // 사용 적정 금액
        double totalRisk,        // 종합 위험도
        List<CategorySpendDto> categories,   // 카테고리별 지출 정보 담을 것 List
        String message
) {
    // 오늘의 지출 정보를 위한 내부 클래스
    public record CategorySpendDto(
            String categoryName,
            int todayRecommendedAmount, // 오늘의 적정 금액
            int spentAmount,       // 실제 사용 금액
            double risk            // 위험도
    ) {}
}
