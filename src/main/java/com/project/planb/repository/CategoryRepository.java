package com.project.planb.repository;

import com.project.planb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    // category 중복 검증
    boolean existsByCategoryName(String categoryName);

    // 카테고리 이름으로 조회
    Optional<Category> findByCategoryName(String categoryName);

    // 조회시 category id ASC
    List<Category> findAllByOrderByIdAsc();
}
