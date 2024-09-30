package com.project.planb.domain.spend.repository;

import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.spend.entity.Spend;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SpendQRepository {
    List<Spend> searchSpends(Long memberId, LocalDate startDate, LocalDate endDate, Long categoryId, Integer minAmount, Integer maxAmount, Boolean isExcludedSum);

    List<Spend> getSpendsByDateRange(Long memberId, LocalDate startDate, LocalDate endDate);

    Map<Category, Integer> getTotalSpendByDateRangeGroupByCategory(Long memberId, LocalDate startDate, LocalDate endDate);
}
