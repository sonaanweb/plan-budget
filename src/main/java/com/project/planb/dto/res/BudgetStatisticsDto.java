package com.project.planb.dto.res;

import java.util.List;

public record BudgetStatisticsDto(

        /**
         * 월 예산 지출 사용량 통계 응답 dto
         */
        int totalBudget, // 설정 예산
        int remainingBudget, // 남은 예산
        double usagePercentage, // 사용 비율
        List<CategoryUsageRes> categoryUsages
){
    public record CategoryUsageRes(
            String categoryName,
            int spentAmount,    // 사용 금액
            int budgetAmount,   // 해당 카테고리의 예산
            double usagePercentage  // 사용 비율
    ) {}
}

