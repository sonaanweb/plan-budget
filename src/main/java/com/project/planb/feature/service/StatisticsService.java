package com.project.planb.feature.service;

import com.project.planb.feature.dto.req.StatisticsPeriodReqDto;
import com.project.planb.feature.dto.res.BudgetStatisticsDto;
import com.project.planb.feature.dto.res.StatisticsDto;
import com.project.planb.domain.budget.entity.Budget;
import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.budget.repository.BudgetRepository;
import com.project.planb.domain.category.repository.CategoryRepository;
import com.project.planb.domain.spend.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final CategoryRepository categoryRepository;
    private final SpendRepository spendRepository;
    private final BudgetRepository budgetRepository;

    /**
     * 지난 달 대비 지출 통계 계산
     */

    // Monthly
    public StatisticsDto compareWithLastMonth(Member member) {

        // 이번 달 오늘까지 사용한 지출액
        LocalDate today = LocalDate.now();
        Map<Category, Integer> currentTotalSpendByCategory =
                spendRepository.getTotalSpendByDateRangeGroupByCategory(
                        member.getId(), today.withDayOfMonth(1), today);

        // 지난달 오늘까지 사용한 지출액
        LocalDate lastToday = today.minusMonths(1);
        Map<Category, Integer> lastTotalSpendByCategory =
                spendRepository.getTotalSpendByDateRangeGroupByCategory(
                        member.getId(), lastToday.withDayOfMonth(1), lastToday);

        return getStatisticsDto(currentTotalSpendByCategory, lastTotalSpendByCategory);
    }

    /**
     * 지난 요일 대비 지출 통계 계산 -- N요일 -> 주간으로 변경
     */

    // Weekly
    public StatisticsDto compareWithLastWeek(Member member) {

        // 오늘 날짜
        LocalDate today = LocalDate.now();

        // 주간 시작일 = 월요일
        LocalDate startOfCurrentWeek = today.with(java.time.DayOfWeek.MONDAY); // 이번주
        LocalDate startOfLastWeek = startOfCurrentWeek.minusWeeks(1); // 지난주

        // 이번 주 지출액 (시작일인 월요일부터 `오늘`)
        Map<Category, Integer> currentTotalSpendByCategory =
                spendRepository.getTotalSpendByDateRangeGroupByCategory(
                        member.getId(), startOfCurrentWeek, today);

        // 지난 주 지출액 (월 ~ 일)
        // plusDays (월요일부터 1일이므로 plusDays +6 == 일요일)
        Map<Category, Integer> lastTotalSpendByCategory =
                spendRepository.getTotalSpendByDateRangeGroupByCategory(
                        member.getId(), startOfLastWeek, startOfLastWeek.plusDays(6));

        return getStatisticsDto(currentTotalSpendByCategory, lastTotalSpendByCategory);
    }

    // 카테고리별 통계 정보를 가져오는 메서드
    private StatisticsDto getStatisticsDto(Map<Category, Integer> current, Map<Category, Integer> last) {
        int lastTotalSpend = 0;
        int currentTotalSpend = 0;
        List<StatisticsDto.CategoryResDto> categoryResDto = new ArrayList<>();

        for (Category category : categoryRepository.findAll()) {
            int currentSpend = current.getOrDefault(category, 0);
            int lastSpend = last.getOrDefault(category, 0);

            // 증가율 계산
            int increaseRate = calculateIncreaseRate(lastSpend, currentSpend);

            categoryResDto.add(new StatisticsDto.CategoryResDto(
                    category.getCategoryName(),
                    lastSpend,
                    currentSpend,
                    increaseRate
            ));

            lastTotalSpend += lastSpend;
            currentTotalSpend += currentSpend;
        }

        // 전체 지출액 증가율 계산
        int totalIncreaseRate = calculateIncreaseRate(lastTotalSpend, currentTotalSpend);

        return new StatisticsDto(
                lastTotalSpend,
                currentTotalSpend,
                totalIncreaseRate,
                categoryResDto
        );
    }

    private int calculateIncreaseRate(int lastValue, int currentValue) {
        if (lastValue == 0) return 0; // 이전 값이 0일 때는 0 반환할 것
        return (int) (((double) (currentValue - lastValue) / lastValue) * 100); // 증가율 계산
    }


    /**
     * 특정 연도와 월의 예산 사용량 통계 조회
     */
    @Transactional(readOnly = true)
    public BudgetStatisticsDto getMonthlyStatistics(Member member, StatisticsPeriodReqDto reqDto) {

        // 현재 날짜 가져오기 ( 기본 조회 )
        LocalDate now = LocalDate.now();

        int year = (reqDto.year() != null) ? reqDto.year() : now.getYear();
        int month = (reqDto.month() != null) ? reqDto.month() : now.getMonthValue();

        // 1. 해당 월의 시작일과 종료일 계산
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // 2. 해당 월의 전체 예산 정보 조회
        List<Budget> monthlyBudgets = budgetRepository.findByMemberAndDateRange(member.getId(), year, month);

        // 3. 해당 월의 카테고리별 지출 조회
        Map<Category, Integer> totalSpendByCategory = spendRepository.getTotalSpendByDateRangeGroupByCategory(
                member.getId(), startDate, endDate);

        // 4. 전체 예산 합계와 남은 예산 계산
        int totalBudget = monthlyBudgets.stream().mapToInt(Budget::getAmount).sum();
        int totalSpent = totalSpendByCategory.values().stream().mapToInt(Integer::intValue).sum();
        int remainingBudget = totalBudget - totalSpent;

        // 5. 사용 비율 계산
        double usagePercentage = (totalBudget == 0) ? 0.0 : ((double) totalSpent / totalBudget) * 100;

        // 6. 각 카테고리별 사용량 및 사용 비율 계산
        List<BudgetStatisticsDto.CategoryUsageRes> categoryUsages = monthlyBudgets.stream()
                .map(budget -> {
                    int spentAmount = totalSpendByCategory.getOrDefault(budget.getCategory(), 0);
                    double categoryUsagePercentage = (budget.getAmount() == 0) ? 0.0 :
                            ((double) spentAmount / budget.getAmount()) * 100;

                    // 소수점 반올림
                    double roundedUsagePercentage = Math.round(categoryUsagePercentage * 100.0) / 100.0;

                    return new BudgetStatisticsDto.CategoryUsageRes(
                            budget.getCategory().getCategoryName(),
                            spentAmount,
                            budget.getAmount(),
                            roundedUsagePercentage
                    );
                }).toList();

        // 비율 반올림
        double roundedTotalUsagePercentage = Math.round(usagePercentage * 100.0) / 100.0;

        return new BudgetStatisticsDto(totalBudget, remainingBudget, roundedTotalUsagePercentage, categoryUsages);
    }
}
