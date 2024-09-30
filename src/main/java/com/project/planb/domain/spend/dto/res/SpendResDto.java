package com.project.planb.domain.spend.dto.res;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record SpendResDto(
        Integer totalAmount, // 총 지출액
        Map<Long, Integer> categoryAmounts, // 카테고리별 지출 총액
        List<SpendList> spendList // 각 지출 정보
) {
    // 지출 정보를 위한 내부 클래스 (SpendList) = 목록 조회
    public record SpendList(
            Long id,
            LocalDate spendAt,
            Long categoryId,
            Integer amount,
            String memo,
            Boolean isExcludedSum
    ) {}
}
