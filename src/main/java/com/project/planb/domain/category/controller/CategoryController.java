package com.project.planb.domain.category.controller;

import com.project.planb.domain.category.dto.CategoryResDto;
import com.project.planb.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="예산 지출 카테고리 API", description = "등록 되어있는 기본 카테고리 목록 반환")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 목록 반환
    @Operation(summary = "카테고리 리스트")
    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getCategories(){
        List<CategoryResDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
