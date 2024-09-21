package com.project.planb.service;

import com.project.planb.dto.res.CategoryResDto;
import com.project.planb.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
