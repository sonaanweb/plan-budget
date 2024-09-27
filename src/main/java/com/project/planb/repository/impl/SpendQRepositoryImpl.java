package com.project.planb.repository.impl;

import com.project.planb.entity.Category;
import com.project.planb.entity.QSpend;
import com.project.planb.entity.Spend;
import com.project.planb.repository.SpendQRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SpendQRepositoryImpl implements SpendQRepository {

    private final JPAQueryFactory queryFactory;

    // 지출 목록 조회
    @Override
    public List<Spend> searchSpends(Long memberId, LocalDate startDate, LocalDate endDate, Long categoryId, Integer minAmount, Integer maxAmount, Boolean isExcludedSum) {

        // Querydsl을 사용한 동적 쿼리 작성
        QSpend spend = QSpend.spend; // QSpend 인스턴스 생성

        BooleanBuilder builder = new BooleanBuilder();

        // 필수 조건 = memberId, 날짜 (but 입력 안 했을 시 현재 달)
        builder.and(spend.member.id.eq(memberId));
        builder.and(spend.spendAt.between(startDate, endDate));

        // 선택적 조건 추가
        if (categoryId != null) {
            builder.and(spend.category.id.eq(categoryId));
        }
        if (minAmount != null) {
            builder.and(spend.amount.goe(minAmount));
        }
        if (maxAmount != null) {
            builder.and(spend.amount.loe(maxAmount));
        }
        if (isExcludedSum != null) {
            builder.and(spend.isExcludedSum.eq(isExcludedSum));
        }

        return queryFactory
                .selectFrom(spend)
                .where(builder)
                .orderBy(spend.spendAt.desc())
                .fetch();
    }

/* 오늘의 지출 안내
    @Override
    public List<Spend> findTodaySpends(Long memberId, LocalDate today) {
        QSpend spend = QSpend.spend;
        return queryFactory
                .selectFrom(spend)
                .where(spend.member.id.eq(memberId)
                        .and(spend.spendAt.eq(today)))
                .fetch();
    }
*/

    /**
     * 통계
     */

    // 날짜 범위 내에서 해당 사용자의 지출 목록 LIST
    @Override
    public List<Spend> getSpendsByDateRange(Long memberId, LocalDate startDate, LocalDate endDate) {
        QSpend spend = QSpend.spend;

        return queryFactory
                .selectFrom(spend)
                .where(spend.member.id.eq(memberId)
                        .and(spend.spendAt.between(startDate, endDate)))
                .orderBy(spend.spendAt.desc())
                .fetch();
    }

    // 지출한 금액을 카테고리별로 집계하여 총합 반환 MAP
    @Override
    public Map<Category, Integer> getTotalSpendByDateRangeGroupByCategory(Long memberId, LocalDate startDate, LocalDate endDate) {
        QSpend spend = QSpend.spend;

        // 쿼리
        List<Tuple> results = queryFactory
                .select(spend.category, spend.amount.sum())
                .from(spend)
                .where(spend.member.id.eq(memberId)
                        .and(spend.spendAt.between(startDate, endDate)))
                .groupBy(spend.category) // 카테고리별
                .fetch();

        // 결과 Map으로 변환
        return results.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(spend.category),
                        tuple -> tuple.get(spend.amount.sum()) // 해당 카테고리의 총 지출액
                ));
    }
}