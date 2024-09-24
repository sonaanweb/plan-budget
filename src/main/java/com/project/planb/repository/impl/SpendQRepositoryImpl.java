package com.project.planb.repository.impl;

import com.project.planb.entity.QSpend;
import com.project.planb.entity.Spend;
import com.project.planb.repository.SpendQRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpendQRepositoryImpl implements SpendQRepository {

    private final JPAQueryFactory queryFactory;

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
                .fetch();
    }
}