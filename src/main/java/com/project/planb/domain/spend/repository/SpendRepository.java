package com.project.planb.domain.spend.repository;

import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.spend.entity.Spend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpendRepository extends JpaRepository<Spend, Long>, SpendQRepository {

    // 지출 상세
    Optional<Spend> findByIdAndMemberId(Long spendId, Long memberId);

    // 오늘의 지출 - 단순 조회용이므로 queryDsl 사용 X
    @Query("SELECT s FROM Spend s WHERE s.member = :member AND s.spendAt = :today")
    List<Spend> findTodaySpends(@Param("member") Member member, @Param("today") LocalDate today);
}
