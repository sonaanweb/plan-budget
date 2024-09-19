package com.project.planb.config;

import com.project.planb.entity.Category;
import com.project.planb.enums.CategoryType;
import com.project.planb.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryInit {
    /*
    동적 쿼리를 구성할 것이기 때문에 SQL파일을 추가하기보단 JAVA 초기화 클래스 생성 선택
    */
    private final CategoryRepository categoryRepository;

    @Transactional
    @PostConstruct
    public void init(){
        List<Category> categories = new ArrayList<>();

        for(CategoryType type: CategoryType.values()){
            if (!categoryRepository.existsByCategoryName(type.getCategoryName())) {
                categories.add(Category.builder()
                        .categoryName(type.getCategoryName())
                        .build());
            }
        }

        if (!categories.isEmpty()){
            categoryRepository.saveAll(categories);
        }
    }
}
