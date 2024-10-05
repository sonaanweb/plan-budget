package com.project.planb.feature.service;

import com.project.planb.domain.spend.dto.res.TodaySpendDto;
import com.project.planb.domain.spend.dto.res.TodaySpendDto.CategorySpendDto;
import com.project.planb.domain.budget.entity.Budget;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.spend.entity.Spend;
import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.domain.budget.repository.BudgetRepository;
import com.project.planb.domain.member.repository.MemberRepository;
import com.project.planb.domain.spend.repository.SpendRepository;
import com.project.planb.common.utils.NotificationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        // 오늘의 지출 데이터가 없는 경우
        if (spends.isEmpty()) {
            return new TodaySpendDto(0, 0, 0.0, List.of(), Optional.of("지출 데이터가 없습니다."), List.of());
        }

        // 3. 총 지출 금액 계산
        int totalSpentAmount = spends.stream()
                .mapToInt(Spend::getAmount)
                .sum();

        // 4. 카테고리별 지출 정보 및 위험도 계산
        Map<Long, List<Spend>> categorySpendMap = spends.stream()
                .collect(Collectors.groupingBy(spend -> spend.getCategory().getId()));

        List<CategorySpendDto> categorySpendDto = new ArrayList<>();
        List<String> unBudgetCategories = new ArrayList<>();

        for (Map.Entry<Long, List<Spend>> entry : categorySpendMap.entrySet()) {
            Long categoryId = entry.getKey();
            List<Spend> categorySpends = entry.getValue();

            // 해당 카테고리의 오늘 지출 금액
            int spentAmount = categorySpends.stream()
                    .mapToInt(Spend::getAmount)
                    .sum();

            // 해당 월의 예산(카테고리) 하루 예산 계산 로직 = 총액 / 일수(lengthOfMonth)
            Optional<Budget> budgetOptional = budgetRepository.findByMemberAndCategoryAndYearAndMonth(
                    member.getId(), categoryId, today.getYear(), today.getMonthValue()
            );

            // 예산에 등록되지 않은 카테고리 처리
            if (budgetOptional.isEmpty()) {
                log.warn("예산 등록되지 않은 카테고리가 있습니다: {}", categoryId);
                String categoryName = entry.getValue().get(0).getCategory().getCategoryName();
                unBudgetCategories.add(categoryName); // 예산 미등록 카테고리 수집
                categorySpendDto.add(new CategorySpendDto(categoryName, 0, spentAmount, 0)); // 기본값 처리
                continue; // 다음 카테고리로 넘어감
            }

            Budget budget = budgetOptional.get();
            int dailyBudget = (int) Math.round((double) budget.getAmount() / today.lengthOfMonth());

            // 위험도 계산
            int risk = (int) Math.round((double) spentAmount / dailyBudget * 100); // 반올림

            categorySpendDto.add(new CategorySpendDto(entry.getValue().get(0).getCategory().getCategoryName(), dailyBudget, spentAmount, risk));
        }

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
        return new TodaySpendDto(totalSpentAmount, recommendedAmount, totalRisk, categorySpendDto, Optional.empty(), unBudgetCategories);
    }

    @Transactional
    @Scheduled(cron = "0 0 20 * * ?") // 매일 20:00에 실행
    public void sendDailyNotifications() {
        List<Member> members = memberRepository.findAll();

        // 알림 승인 회원에게만 전송 필터링
        List<Member> notifiedMembers = members.stream()
                .filter(Member::getNotificationEnabled)
                .collect(Collectors.toList());

        for (Member member : notifiedMembers) {
            // 각 회원의 오늘 지출 정보 가져오기
            TodaySpendDto todaySpend = getTodaySpend(member);
            String message = NotificationUtils.createNotificationMessage(member, todaySpend);

            log.info("회원 {}의 오늘 지출 알림: {}", member.getId(), message);
        }
    }
}