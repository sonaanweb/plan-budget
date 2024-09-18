package com.project.planb.controller;

import com.project.planb.dto.req.CategoryCreateReqDto;
import com.project.planb.dto.res.CategoryResDto;
import com.project.planb.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    // todo : 사용자 인증 필터 추가
    @PostMapping
    public ResponseEntity<CategoryResDto> createCategory(@RequestBody @Valid CategoryCreateReqDto reqDto) {
        CategoryResDto createdCategory = categoryService.createCategory(reqDto);
        return ResponseEntity.ok(createdCategory);
    }

    // 카테고리 목록 반환
    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getCategories(){
        List<CategoryResDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
