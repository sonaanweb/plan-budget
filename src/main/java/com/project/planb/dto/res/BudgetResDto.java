package com.project.planb.dto.res;

import java.util.List;

public record BudgetResDto(
        int totalAmount,            // 총 예산
        List<BudgetDetail> budgets  // 예산 리스트
) {
    public record BudgetDetail(
            Long id,
            String categoryName,
            int year,
            int month,
            Integer amount
    ) {}
}
