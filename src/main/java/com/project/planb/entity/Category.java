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
    /*
    유저가 사용할 카테고리 엔티티 고정된 게 아니므로 enum 대신 string 사용
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Builder
    public Category(String categoryName){
        this.categoryName = categoryName;
    }
}
