package com.project.planb.service;

import com.project.planb.dto.res.TodaySpendDto;
import com.project.planb.dto.res.TodaySpendDto.CategorySpendDto;
import com.project.planb.entity.Budget;
import com.project.planb.entity.Member;
import com.project.planb.entity.Spend;
import com.project.planb.exception.CustomException;
import com.project.planb.exception.ErrorCode;
import com.project.planb.repository.BudgetRepository;
import com.project.planb.repository.MemberRepository;
import com.project.planb.repository.SpendRepository;
import com.project.planb.utils.NotificationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsultingService {

    private final SpendRepository spendRepository;
    private final BudgetRepository budgetRepository;
    private final MemberRepository memberRepository;

    public TodaySpendDto getTodaySpend(Member member) {
        // 1. 오늘 날짜
        LocalDate today = LocalDate.now();

        log.info("today : {}", today);

        // 2. 오늘의 지출 내역 가져오기 (카테고리별)
        List<Spend> spends = spendRepository.findTodaySpends(member, today);

        log.info("today spends : {}", spends);

        // 3. 총 지출 금액 계산
        int totalSpentAmount = spends.stream()
                .mapToInt(Spend::getAmount)
                .sum();

        // 4. 카테고리별 지출 정보 및 위험도 계산
        Map<Long, List<Spend>> categorySpendMap = spends.stream()
                .collect(Collectors.groupingBy(spend -> spend.getCategory().getId()));

        List<CategorySpendDto> categorySpendDto = categorySpendMap.entrySet()
                .stream()
                .map(entry -> {
                    Long categoryId = entry.getKey();
                    List<Spend> categorySpends = entry.getValue();

                    // 해당 카테고리의 오늘 지출 금액
                    int spentAmount = categorySpends.stream()
                            .mapToInt(Spend::getAmount)
                            .sum();

                    // 해당 월의 예산(카테고리) 하루 예산 계산 로직 = 총액 / 일수(lengthOfMonth)
                    Budget budget = budgetRepository.findByMemberAndCategoryAndYearAndMonth(
                            member.getId(), categoryId, today.getYear(), today.getMonthValue()
                    ).orElseThrow(() -> new CustomException(ErrorCode.BUDGET_NOT_FOUND));

                    int dailyBudget = (int) Math.round((double) budget.getAmount() / today.lengthOfMonth());

                    // 위험도 계산
                    int risk = (int) Math.round((double) spentAmount / dailyBudget * 100); // 반올림

                    return new CategorySpendDto(entry.getValue().get(0).getCategory().getCategoryName(), dailyBudget, spentAmount, risk);
                })
                .collect(Collectors.toList());

        // 5. 사용 추천 금액(total)
        int recommendedAmount = categorySpendDto.stream()
                .mapToInt(CategorySpendDto::todayRecommendedAmount)
                .sum();

        // 6. total 위험도는 모든 카테고리의 위험도를 평균값으로 구한다
        double totalRisk = categorySpendDto.stream()
                .mapToDouble(CategorySpendDto::risk)
                .average()
                .orElse(0);

        // 응답값
        return new TodaySpendDto(totalSpentAmount, recommendedAmount, totalRisk, categorySpendDto);
    }

    @Transactional
    // @Scheduled(cron = "0 * * * * ?") 1분 TEST
    @Scheduled(cron = "0 0 20 * * ?") // 매일 20:00에 실행
    public void sendDailyNotifications() {
        List<Member> members = memberRepository.findAll(); // TEST : 모든 회원 todo: 알림 승인한 회원만 + 예외처리 필요

        for (Member member : members) {
            // 각 회원의 오늘 지출 정보 가져오기
            TodaySpendDto todaySpend = getTodaySpend(member);
            String message = NotificationUtils.createNotificationMessage(member, todaySpend);

            log.info("회원 {}의 오늘 지출 알림: {}", member.getId(), message);
        }
    }
}