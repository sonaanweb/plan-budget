package com.project.planb.controller;

import com.project.planb.dto.res.CategoryResDto;
import com.project.planb.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 목록 반환
    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getCategories(){
        List<CategoryResDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
