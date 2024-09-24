package com.project.planb.repository;

import com.project.planb.entity.Member;
import com.project.planb.entity.Spend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpendRepository extends JpaRepository<Spend, Long> {
    Optional<Spend> findByIdAndMember(Long spendId, Member member);
}
