package com.project.planb.domain.budget.entity;

import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.member.entity.Member;
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

    @Column(name = "budget_amount", nullable = false)
    private Integer amount;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Builder
    public Budget(Member member, Category category, Integer amount, Integer year, Integer month) {
        this.member = member;
        this.category = category;
        this.amount = amount != null ? amount : 0;
        this.year = year;
        this.month = month;
    }
}
