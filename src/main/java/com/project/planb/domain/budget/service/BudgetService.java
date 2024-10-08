package com.project.planb.domain.budget.service;

import com.project.planb.domain.budget.dto.req.BudgetCreateReqDto;
import com.project.planb.domain.budget.dto.req.BudgetPeriodReqDto;
import com.project.planb.domain.budget.dto.res.BudgetCreateResDto;
import com.project.planb.domain.budget.dto.res.BudgetResDto;
import com.project.planb.domain.budget.entity.Budget;
import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.domain.budget.repository.BudgetRepository;
import com.project.planb.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    // 예산 생성
    @Transactional
    public BudgetCreateResDto createBudget(BudgetCreateReqDto budgetCreateReqDto, Member member) {
        Category category = categoryRepository.findById(budgetCreateReqDto.categoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // 중복 예외 처리: 같은 년도, 같은 달, 같은 카테고리 중복 체크
        if (budgetRepository.existsByMemberIdAndCategoryIdAndYearAndMonth(
                member.getId(),
                category.getId(),
                budgetCreateReqDto.year(),
                budgetCreateReqDto.month()
        )) {
            throw new CustomException(ErrorCode.BUDGET_ALREADY_EXISTS);
        }

        Budget budget = Budget.builder()
                .member(member)
                .category(category)
                .amount(budgetCreateReqDto.amount())
                .year(budgetCreateReqDto.year())
                .month(budgetCreateReqDto.month())
                .build();

        budgetRepository.save(budget);

        return new BudgetCreateResDto(
                budget.getId(),
                category.getCategoryName(),
                budget.getYear(),
                budget.getMonth(),
                budget.getAmount()
        );
    }

    /* 예산 조회
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
                .toList();
    } */

    // 년 월 예산 조회
    public BudgetResDto getBudgetsByMemberAndDate(Member member, BudgetPeriodReqDto budgetPeriodReqDto) {
        int year = (budgetPeriodReqDto.year() != null) ? budgetPeriodReqDto.year() : LocalDate.now().getYear();
        int month = (budgetPeriodReqDto.month() != null) ? budgetPeriodReqDto.month() : LocalDate.now().getMonthValue();

        List<Budget> budgets = budgetRepository.findByMemberIdAndYearAndMonth(member.getId(), year, month);

        // 총 예산 계산
        int totalAmount = budgets.stream()
                .mapToInt(Budget::getAmount)
                .sum();

        List<BudgetResDto.BudgetDetail> budgetDetails = budgets.stream()
                .map(budget -> new BudgetResDto.BudgetDetail(
                        budget.getId(),
                        budget.getCategory().getCategoryName(),
                        budget.getYear(),
                        budget.getMonth(),
                        budget.getAmount()
                ))
                .toList();

        return new BudgetResDto(totalAmount, budgetDetails);
    }
}