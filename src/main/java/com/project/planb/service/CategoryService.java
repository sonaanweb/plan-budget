package com.project.planb.service;

import com.project.planb.dto.req.CategoryCreateReqDto;
import com.project.planb.dto.res.CategoryResDto;
import com.project.planb.entity.Category;
import com.project.planb.exception.CustomException;
import com.project.planb.exception.exceptionType.CategoryExceptionType;
import com.project.planb.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    public CategoryResDto createCategory(CategoryCreateReqDto reqDto) {
        if (categoryRepository.existsByCategoryName(reqDto.categoryName())) {
            throw new CustomException(CategoryExceptionType.DUPLICATED_CATEGORY);
        }

        Category category = new Category(reqDto.categoryName());
        categoryRepository.save(category);
        return new CategoryResDto((category.getId()), category.getCategoryName());
    }

    // 카테고리 삭제 -- id값
    public void deleteCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new CustomException(CategoryExceptionType.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

    // 카테고리 목록 반환
    public List<CategoryResDto> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryResDto(category.getId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }
}
