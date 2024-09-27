package com.project.planb.dto.res;

public record BudgetCreateResDto(
        Long id,
        String categoryName,
        int year,
        int month,
        Integer amount
) {}