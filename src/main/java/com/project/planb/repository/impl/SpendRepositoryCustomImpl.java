package com.project.planb.repository;

import com.project.planb.entity.QSpend;
import com.project.planb.entity.Spend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpendRepositoryCustomImpl implements SpendRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Spend> searchSpends(Long memberId, LocalDate startDate, LocalDate endDate, Long categoryId, Integer minAmount, Integer maxAmount, Boolean isExcludedSum) {

        // Querydsl을 사용한 동적 쿼리 작성
        return queryFactory
                .selectFrom(QSpend.spend)
                .where(
                        QSpend.spend.member.id.eq(memberId),
                        QSpend.spend.spendAt.between(startDate, endDate),
                        categoryId != null ? QSpend.spend.category.id.eq(categoryId) : null,
                        minAmount != null ? QSpend.spend.amount.goe(minAmount) : null,
                        maxAmount != null ? QSpend.spend.amount.loe(maxAmount) : null,
                        isExcludedSum != null ? QSpend.spend.isExcludedSum.eq(isExcludedSum) : null
                )
                .fetch();
    }
}