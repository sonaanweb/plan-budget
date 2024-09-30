package com.project.planb.service;

import com.project.planb.domain.category.service.CategoryService;
import com.project.planb.domain.category.dto.CategoryResDto;
import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.category.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class) // JUnit 5 - Mockito
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @DisplayName("카테고리 목록 조회 테스트")
    @Test
    void getAllCategories() {
        Category category1 = new Category(1L, "Food");
        Category category2 = new Category(2L, "Transport");

        // order ASC 해줬기때문에 순서 바뀌면 fail (success)
        when(categoryRepository.findAllByOrderByIdAsc()).thenReturn(Arrays.asList(category1, category2));

        List<CategoryResDto> result = categoryService.getAllCategories();

        log.info("result {}", result);

        assertEquals(2, result.size());
        assertEquals(new CategoryResDto(1L, "Food"), result.get(0));
        assertEquals(new CategoryResDto(2L, "Transport"), result.get(1));
    }
}
