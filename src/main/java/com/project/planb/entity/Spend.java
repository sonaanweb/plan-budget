package com.project.planb.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 일자만 필요하므로 LocalDate
    @Column
    private LocalDate spendAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "spend_amount", nullable = false)
    private Integer amount;

    private String memo;

    // 합계 제외
    @Column(nullable = false)
    private Boolean isExcludedSum;

    @Builder
    public Spend(Member member, LocalDate spendAt, Category category, Integer amount, String memo, Boolean isExcludedSum){
        this.member = member;
        this.spendAt = spendAt;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
        this.isExcludedSum = isExcludedSum != null ? isExcludedSum : false;  // 기본값 false(제외하지 않음)
    }

    // 수정 메서드
    public void spendUpdate(LocalDate spendAt, Category category, Integer amount, String memo, Boolean isExcludedSum) {
        this.spendAt = spendAt;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
        this.isExcludedSum = isExcludedSum != null ? isExcludedSum : false;  // 기본값 false(제외하지 않음)
    }
}
