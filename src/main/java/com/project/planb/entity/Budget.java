package com.project.planb.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // 예산 총액
    @Column(name = "budget_amount", nullable = false)
    private Integer amount;

    // 통계 비율
    @Column(name = "rate", nullable = false)
    private Double rate;

    @Builder
    public Budget(Member member, Category category, Integer amount, Double rate) {
        this.member = member;
        this.category = category;
        this.amount = amount != null ? amount : 0;
        this.rate = rate != null ? rate : 0;
    }

    public void updateAmount(Integer amount) {
        this.amount = amount;
    }

    public void updateRate(Double rate) {
        this.rate = rate;
    }
}
