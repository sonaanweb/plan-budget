package com.project.planb.domain.spend.dto.res;

import java.time.LocalDate;

public record SpendDetailDto(
        Long id,
        LocalDate spendAt,
        Long categoryId,
        String categoryName,
        Integer amount,
        String memo,
        Boolean isExcludedSum
) {}