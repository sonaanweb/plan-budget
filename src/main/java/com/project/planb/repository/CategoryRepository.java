package com.project.planb.repository;

import com.project.planb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    // category 중복 검증
    boolean existsByCategoryName(String categoryName);
}
