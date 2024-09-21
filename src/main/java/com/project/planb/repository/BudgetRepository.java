package com.project.planb.repository;

import com.project.planb.entity.Budget;
import com.project.planb.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // 예산 생성 조회
    List<Budget> findByMember(Member member);
}
