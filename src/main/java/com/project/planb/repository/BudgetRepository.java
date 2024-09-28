package com.project.planb.repository;

import com.project.planb.entity.Budget;
import com.project.planb.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // 예산 생성 조회 (전체)
    List<Budget> findByMember(Member member);

    // 예산 생성 조회 년 월
    List<Budget> findByMemberIdAndYearAndMonth(Long memberId, Integer year, Integer month);

    // 예산 년/월 중복 카테고리 생성 불가
    boolean existsByMemberIdAndCategoryIdAndYearAndMonth(Long memberId, Long categoryId, int year, int month);

    // 특정 카테고리와 특정 연도/월에 대한 예산 조회 > 예산 조회는 이거 하나이므로 따로 queryDsl 파일 X
    @Query("SELECT b FROM Budget b WHERE b.member.id = :memberId AND b.category.id = :categoryId AND b.year = :year AND b.month = :month")
    Optional<Budget> findByMemberAndCategoryAndYearAndMonth(
            @Param("memberId") Long memberId,
            @Param("categoryId") Long categoryId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );

    // 특정 연도와 월의 예산 조회 (월 사용량, 예산 대비 지출 통계)
    @Query("SELECT b FROM Budget b WHERE b.member.id = :memberId AND b.year = :year AND b.month = :month")
    List<Budget> findByMemberAndDateRange(
            @Param("memberId") Long memberId,
            @Param("year") Integer year,
            @Param("month") Integer month
    );
}
