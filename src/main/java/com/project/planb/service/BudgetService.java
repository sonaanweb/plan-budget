package com.project.planb.service;

import com.project.planb.dto.req.BudgetCreateReqDto;
import com.project.planb.dto.req.BudgetFilterReqDto;
import com.project.planb.dto.res.BudgetCreateResDto;
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
        if (budgetRepository.existsByMemberAndCategoryIdAndYearAndMonth(
                member,
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
    public BudgetResDto getBudgetsByMemberAndDate(Member member, BudgetFilterReqDto budgetFilterReqDto) {
        int year = (budgetFilterReqDto.year() != 0) ? budgetFilterReqDto.year() : LocalDate.now().getYear();
        int month = (budgetFilterReqDto.month() != 0) ? budgetFilterReqDto.month() : LocalDate.now().getMonthValue();

        List<Budget> budgets = budgetRepository.findByMemberAndYearAndMonth(member, year, month);

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