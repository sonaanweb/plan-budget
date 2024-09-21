package com.project.planb.dto.res;

public record BudgetResDto(
        Long id,
        String categoryName,
        int year,
        int month,
        Integer amount
) {
}
