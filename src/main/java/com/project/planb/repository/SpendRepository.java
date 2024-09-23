package com.project.planb.repository;

import com.project.planb.entity.Member;
import com.project.planb.entity.Spend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpendRepository extends JpaRepository<Spend, Long> {

    // 지출 생성 조회
    List<Spend> findByMember(Member member);
}
