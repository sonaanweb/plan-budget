package com.project.planb.controller;

import com.project.planb.dto.req.StatisticsPeriodReqDto;
import com.project.planb.dto.res.BudgetStatisticsDto;
import com.project.planb.dto.res.StatisticsDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "통계 API", description = "주간/월간 지출 통계 API")
@RestController
@RequestMapping("/api/statics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * 지출 통계: 지난 달과 비교
     */
    @Operation(summary = "지난 달과 현재 지출 비교")
    @GetMapping("/monthly")
    public ResponseEntity<StatisticsDto> compareWithLastMonth(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        StatisticsDto statistics = statisticsService.compareWithLastMonth(member);
        return ResponseEntity.ok(statistics);
    }

    /**
     * 지출 통계: 지난 주와 비교
     */
    @Operation(summary = "지난 주와 현재 지출 비교")
    @GetMapping("/weekly")
    public ResponseEntity<StatisticsDto> compareWithLastWeek(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        StatisticsDto statistics = statisticsService.compareWithLastWeek(member);
        return ResponseEntity.ok(statistics);
    }

    /**
     * 현재 예산 사용량, 남은 예산 통계
     */
    @Operation(summary = "월 예산 사용량 및 남은 예산 통계 조회")
    @GetMapping("/budgets")
    public ResponseEntity<BudgetStatisticsDto> getMonthlyBudgetStatistics(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        Member member = principalDetails.getMember();
        // StatisticsPeriodReqDto 객체 생성
        StatisticsPeriodReqDto reqDto = new StatisticsPeriodReqDto(year, month);

        BudgetStatisticsDto budgetStatistics = statisticsService.getMonthlyStatistics(member, reqDto);
        return ResponseEntity.ok(budgetStatistics);
    }
}
