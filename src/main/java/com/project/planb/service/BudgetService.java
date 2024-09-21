package com.project.planb.service;

import com.project.planb.dto.req.BudgetCreateReqDto;
import com.project.planb.dto.res.BudgetResDto;
import com.project.planb.entity.Budget;
import com.project.planb.entity.Category;
import com.project.planb.entity.Member;
import com.project.planb.exception.CustomException;
import com.project.planb.exception.ErrorCode;
import com.project.planb.repository.BudgetRepository;
import com.project.planb.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    // 예산 생성
    @Transactional
    public BudgetResDto createBudget(BudgetCreateReqDto budgetCreateReqDto, Member member) {
        Category category = categoryRepository.findById(budgetCreateReqDto.categoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Budget budget = Budget.builder()
                .member(member)
                .category(category)
                .amount(budgetCreateReqDto.amount())
                .year(budgetCreateReqDto.year())
                .month(budgetCreateReqDto.month())
                .build();

        budgetRepository.save(budget);

        // 평균 갱신 메서드 호출
        updateAverageRate(category);

        return new BudgetResDto(
                budget.getId(),
                category.getCategoryName(),
                budget.getYear(),
                budget.getMonth(),
                budget.getAmount()
        );
    }

    // 예산 조회
    public List<BudgetResDto> getBudgetsByMember(Member member) {
        List<Budget> budgets = budgetRepository.findByMember(member);
        return budgets.stream()
                .map(budget -> new BudgetResDto(
                        budget.getId(),
                        budget.getCategory().getCategoryName(),
                        budget.getYear(),
                        budget.getMonth(),
                        budget.getAmount()
                ))
                .toList(); // toList (java 16이상)
    }

    // 카테고리별 예산 평균 메서드
    private void updateAverageRate(Category category) {
        List<Budget> budgets = budgetRepository.findByCategory(category);
        double average = budgets.stream()
                .mapToInt(Budget::getAmount)
                .average()
                .orElse(0);

        category.updateAverageRate((int) average);
        categoryRepository.save(category); // 카테고리에 저장
    }
}