package com.project.planb.utils;

import com.project.planb.dto.res.TodaySpendDto;
import com.project.planb.entity.Member;

import java.util.stream.Collectors;

public class NotificationUtils {

    /*
    알림 발송 메세지
     */

    public static String createNotificationMessage(Member member, TodaySpendDto todaySpend) {
        return String.format(
                "\n안녕하세요, %s님! 오늘의 지출 정보입니다:\n총 지출: %d원\n추천 지출: %d원\n위험도: %.2f%%\n카테고리별 지출: %s",
                member.getAccount(),
                todaySpend.totalSpentAmount(),
                todaySpend.recommendedAmount(),
                todaySpend.totalRisk(),
                todaySpend.categories().stream()
                        .map(dto -> String.format("%s: %d원 (위험도: %.2f%%)", dto.categoryName(), dto.spentAmount(), dto.risk()))
                        .collect(Collectors.joining(", "))
        );
    }
}
