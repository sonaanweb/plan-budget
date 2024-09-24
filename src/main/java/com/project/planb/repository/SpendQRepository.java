package com.project.planb.repository;

import com.project.planb.entity.Spend;

import java.time.LocalDate;
import java.util.List;

public interface SpendRepositoryCustom {
    List<Spend> searchSpends(Long memberId, LocalDate startDate, LocalDate endDate, Long categoryId, Integer minAmount, Integer maxAmount, Boolean isExcludedSum);
}
