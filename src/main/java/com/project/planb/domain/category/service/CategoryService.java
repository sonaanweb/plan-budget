package com.project.planb.domain.category.service;

import com.project.planb.domain.category.dto.CategoryResDto;
import com.project.planb.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 목록 반환
    public List<CategoryResDto> getAllCategories(){
        // OrderByIdAsc : 조회할 때 enum 열거형 순
        return categoryRepository.findAllByOrderByIdAsc().stream()
                .map(category -> new CategoryResDto(category.getId(), category.getCategoryName()))
                .toList();
    }
}
