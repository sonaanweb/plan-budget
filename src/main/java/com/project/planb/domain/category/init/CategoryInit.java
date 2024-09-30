package com.project.planb.domain.category.init;

import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.category.enums.CategoryType;
import com.project.planb.domain.category.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryInit {

    private final CategoryRepository categoryRepository;

    @Transactional
    @PostConstruct
    public void init() {
        // 이미 존재하는 카테고리 이름을 Set으로 저장하여 중복 확인 최소화
        Set<String> existingCategories = categoryRepository.findAll()
                .stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toSet());

        // 존재하지 않는 카테고리만 새로 생성
        List<Category> categoriesToSave = List.of(CategoryType.values()).stream()
                .filter(type -> !existingCategories.contains(type.getCategoryName()))
                .map(type -> Category.builder()
                        .categoryName(type.getCategoryName())
                        .build())
                .collect(Collectors.toList());

        // 저장할 카테고리가 있는 경우만 저장
        if (!categoriesToSave.isEmpty()) {
            categoryRepository.saveAll(categoriesToSave);
        }
    }
}
