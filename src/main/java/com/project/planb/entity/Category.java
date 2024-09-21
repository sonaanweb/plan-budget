package com.project.planb.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Column(nullable = false)
    Integer averageRate;

    @Builder
    public Category(String categoryName, Integer averageRate){
        this.categoryName = categoryName;
        this.averageRate = averageRate != null ? averageRate : 0;
    }

    public Category(Long id, String categoryName, Integer averageRate) {
        this.id = id;
        this.categoryName = categoryName;
        this.averageRate = averageRate != null ? averageRate : 0;
    }

    // 예산 카테고리 평균값 업데이트
    public void updateAverageRate(Integer averageRate){
        this.averageRate = averageRate;
    }
}