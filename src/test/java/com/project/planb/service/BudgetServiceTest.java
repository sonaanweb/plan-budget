package com.project.planb.service;

import com.project.planb.domain.budget.service.BudgetService;
import com.project.planb.domain.budget.dto.req.BudgetCreateReqDto;
import com.project.planb.domain.budget.dto.res.BudgetCreateResDto;
import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.domain.budget.repository.BudgetRepository;
import com.project.planb.domain.category.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {

    @InjectMocks
    private BudgetService budgetService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BudgetRepository budgetRepository;

    private Member member;
    private Category category;

    @BeforeEach
    public void setUp() {
        // MockitoAnnotations.openMocks(this); // Mockito 초기화 - junit5 ExtendWith 어노테이션으로 인해 삭제
        member = new Member(1L, "testAccount", "1234");
        category = new Category(1L, "Food");
    }

    @DisplayName("예산 생성 성공 테스트")
    @Test
    public void createBudget() {
        // Given
        BudgetCreateReqDto budgetCreateReqDto = new BudgetCreateReqDto(1L, 5000, 2024, 9);

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));
        when(budgetRepository.existsByMemberIdAndCategoryIdAndYearAndMonth(
                member.getId(), category.getId(), 2024, 9)).thenReturn(false); // false = 중복 예산 없음

        // When
        BudgetCreateResDto result = budgetService.createBudget(budgetCreateReqDto, member);
        log.info("result {}", result);

        // Then
        assertEquals(category.getCategoryName(), result.categoryName());
        assertEquals(5000, result.amount());
    }

    @DisplayName("예산 생성 실패 테스트 - 카테고리 존재하지 않음")
    @Test
    public void categoryNotFound() {
        // Given
        BudgetCreateReqDto budgetCreateReqDto = new BudgetCreateReqDto(1L, 5000, 2024, 9);

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.empty()); // empty = 존재하지 않는 카테고리

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            budgetService.createBudget(budgetCreateReqDto, member);
        });
        assertEquals(ErrorCode.CATEGORY_NOT_FOUND, exception.getErrorCode());

        // assertEquals(ErrorCode.DUPLICATED_ACCOUNT, exception.getErrorCode()); - Failed 코드 확인
    }

    @DisplayName("예산 생성 실패 테스트 - 이번 달 이미 등록 된 카테고리")
    @Test
    public void BudgetAlreadyExists() {
        // Given
        BudgetCreateReqDto budgetCreateReqDto = new BudgetCreateReqDto(1L, 5000, 2024, 9);

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));
        when(budgetRepository.existsByMemberIdAndCategoryIdAndYearAndMonth(
                member.getId(), category.getId(), 2024, 9)).thenReturn(true); // true = 중복 예산 있음

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            budgetService.createBudget(budgetCreateReqDto, member);
        });
        assertEquals(ErrorCode.BUDGET_ALREADY_EXISTS, exception.getErrorCode());
    }
}
