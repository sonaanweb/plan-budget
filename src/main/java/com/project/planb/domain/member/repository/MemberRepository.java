package com.project.planb.domain.member.repository;

import com.project.planb.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    // account 필드로 Member 엔티티 조회
    Optional<Member> findByAccount(String account);

    // account 중복 검증
    boolean existsByAccount(String account);
}
