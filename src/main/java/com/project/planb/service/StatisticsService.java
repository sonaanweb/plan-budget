package com.project.planb.service;

import com.project.planb.dto.res.StatisticsDto;
import com.project.planb.entity.Category;
import com.project.planb.entity.Member;
import com.project.planb.repository.CategoryRepository;
import com.project.planb.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatisticsService {

    private final CategoryRepository categoryRepository;
    private final SpendRepository spendRepository;

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
     * 지난 요일 대비 지출 통계 계산 -- TODO: N요일 기준 데이터만 보여주는 게 나을지 주간으로 계산할 지
     */

    // Weekly
    public StatisticsDto compareWithLastWeek(Member member) {

        // 오늘 사용한 지출액
        LocalDate today = LocalDate.now();
        Map<Category, Integer> currentTotalSpendByCategory =
                spendRepository.getTotalSpendByDateRangeGroupByCategory(member.getId(), today, today);

        // 지난주 오늘 사용한 지출액
        LocalDate lastToday = today.minusWeeks(1);
        Map<Category, Integer> lastTotalSpendByCategory =
                spendRepository.getTotalSpendByDateRangeGroupByCategory(member.getId(), lastToday, lastToday);

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
}
