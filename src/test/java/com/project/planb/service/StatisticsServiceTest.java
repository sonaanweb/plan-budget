package com.project.planb.service;

import com.project.planb.dto.req.StatisticsPeriodReqDto;
import com.project.planb.dto.res.BudgetStatisticsDto;
import com.project.planb.dto.res.StatisticsDto;
import com.project.planb.entity.Budget;
import com.project.planb.entity.Category;
import com.project.planb.entity.Member;
import com.project.planb.repository.BudgetRepository;
import com.project.planb.repository.CategoryRepository;
import com.project.planb.repository.SpendRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SpendRepository spendRepository;

    @Mock
    private BudgetRepository budgetRepository;

    private Member member;
    private List<Category> categories;

    @BeforeEach
    public void setUp() {
        member = new Member(1L, "testAccount", "1234");

        // 카테고리 객체 초기화
        categories = Arrays.asList(
                new Category("Food"),
                new Category("Transport")
        );
        when(categoryRepository.findAll()).thenReturn(categories); // 데이터 접근을 위함
    }

    @DisplayName("통계테스트 - 현재/지난 달 같은 카테고리 비교")
    @Test
    public void compareWithLastMonth() {

        /**
         * given
         */
        LocalDate today = LocalDate.now();
        Map<Category, Integer> currentSpend = new HashMap<>();
        Map<Category, Integer> lastSpend = new HashMap<>();

        // 현재 지출 상황 500
        currentSpend.put(categories.get(0), 300); // Food
        currentSpend.put(categories.get(1), 200); // Transport

        // 지난 달 지출 400
        lastSpend.put(categories.get(0), 250);
        lastSpend.put(categories.get(1), 150);

        // any(localDate) 1 - current , 2 - last
        when(spendRepository.getTotalSpendByDateRangeGroupByCategory(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(currentSpend).thenReturn(lastSpend);

        /**
         * when
         */
        StatisticsDto result = statisticsService.compareWithLastMonth(member);

        /**
         * then
         */
        Assertions.assertEquals(500, result.currentAmount()); // 현재 지출 총합
        Assertions.assertEquals(400, result.lastAmount()); // 지난 달 지출 총합
        Assertions.assertEquals(25.0, result.increaseRate()); // 증가율 => ( (현재 지출 - 지난 달 지출) / 지난 달 지출) ) X 100
        Assertions.assertEquals(2, result.categories().size()); // 카테고리 수 검증
    }

    @DisplayName("통계테스트 - 현재/지난 주 같은 카테고리 비교")
    @Test
    public void compareWithLastWeek() {
        /**
         * given
         */
        LocalDate today = LocalDate.now();
        Map<Category, Integer> currentSpend = new HashMap<>();
        Map<Category, Integer> lastSpend = new HashMap<>();

        // 이번 주 지출 상황 600
        currentSpend.put(categories.get(0), 400); // Food
        currentSpend.put(categories.get(1), 200); // Transport

        // 지난 주 지출 500
        lastSpend.put(categories.get(0), 300);
        lastSpend.put(categories.get(1), 200);

        // any(localDate) 1 - current , 2 - last
        when(spendRepository.getTotalSpendByDateRangeGroupByCategory(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(currentSpend).thenReturn(lastSpend);

        /**
         * when
         */
        StatisticsDto result = statisticsService.compareWithLastWeek(member);

        /**
         * then
         */
        Assertions.assertEquals(600, result.currentAmount()); // 현재 지출 총합
        Assertions.assertEquals(500, result.lastAmount()); // 지난 주 지출 총합
        Assertions.assertEquals(20.0, result.increaseRate()); // 증가율 => ( (현재 지출 - 지난 주 지출) / 지난 주 지출) ) X 100
        Assertions.assertEquals(2, result.categories().size()); // 카테고리 수 검증
    }


    @DisplayName("통계테스트 - 특정 월의 예산 사용량 통계 조회")
    @Test
    public void getMonthlyStatistics() {
        /**
         * given 2024-09
         */
        int year = 2024;
        int month = 9;

        // request
        List<Category> categories = categoryRepository.findAll();
        StatisticsPeriodReqDto reqDto = new StatisticsPeriodReqDto(year, month);

        // 예산 설정
        List<Budget> monthlyBudgets = Arrays.asList(
                new Budget(member, categories.get(0), 500, year, month), // Food
                new Budget(member, categories.get(1), 300, year, month)  // Transport
        );

        when(budgetRepository.findByMemberAndDateRange(anyLong(), anyInt(), anyInt()))
                .thenReturn(monthlyBudgets);

        // 지출 설정
        Map<Category, Integer> totalSpendByCategory = new HashMap<>();
        totalSpendByCategory.put(categories.get(0), 200); // Food
        totalSpendByCategory.put(categories.get(1), 100); // Transport

        when(spendRepository.getTotalSpendByDateRangeGroupByCategory(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(totalSpendByCategory);

        /**
         * when
         */
        BudgetStatisticsDto result = statisticsService.getMonthlyStatistics(member, reqDto);

        /**
         * then
         */
        // 전체 예산 및 사용량
        Assertions.assertEquals(800, result.totalBudget()); // 전체 설정 예산 500 + 300
        Assertions.assertEquals(500, result.remainingBudget()); // 남은 예산 800 - 300
        Assertions.assertEquals(37.5, result.usagePercentage()); // 사용 비율 => (지출/예산) * 100

        // 각 `카테고리 사용량` 및 `사용 비율` 검증
        List<BudgetStatisticsDto.CategoryUsageRes> categoryUsages = result.categoryUsages();
        Assertions.assertEquals(2, categoryUsages.size()); // 카테고리 수 = 2

        Assertions.assertEquals("Food", categoryUsages.get(0).categoryName());
        Assertions.assertEquals(200, categoryUsages.get(0).spentAmount()); // 지출 금액
        Assertions.assertEquals(500, categoryUsages.get(0).budgetAmount()); // 카테고리 예산
        Assertions.assertEquals(40.0, categoryUsages.get(0).usagePercentage()); // 사용 비율

        Assertions.assertEquals("Transport", categoryUsages.get(1).categoryName());
        Assertions.assertEquals(100, categoryUsages.get(1).spentAmount());
        Assertions.assertEquals(300, categoryUsages.get(1).budgetAmount());
        Assertions.assertEquals(33.33, categoryUsages.get(1).usagePercentage(), 0.01);
    }
}
