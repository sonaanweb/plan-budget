package com.project.planb.repository;

import com.project.planb.entity.Budget;
import com.project.planb.entity.Category;
import com.project.planb.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // 예산 생성 조회
    List<Budget> findByMember(Member member);

    // 특정 카테고리와 특정 연도/월에 대한 예산 조회 > 예산 조회는 이거 하나이므로 따로 queryDsl 파일 X
    @Query("SELECT b FROM Budget b WHERE b.member.id = :memberId AND b.category.id = :categoryId AND b.year = :year AND b.month = :month")
    Optional<Budget> findByMemberAndCategoryAndYearAndMonth(
            @Param("memberId") Long memberId,
            @Param("categoryId") Long categoryId,
            @Param("year") int year,
            @Param("month") int month
    );
}
