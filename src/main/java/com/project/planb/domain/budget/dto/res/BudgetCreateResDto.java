package com.project.planb.domain.budget.dto.res;

public record BudgetCreateResDto(
        Long id,
        String categoryName,
        int year,
        int month,
        Integer amount
) {}