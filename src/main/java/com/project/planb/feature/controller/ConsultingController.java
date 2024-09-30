package com.project.planb.feature.controller;

import com.project.planb.domain.spend.dto.res.TodaySpendDto;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.common.security.PrincipalDetails;
import com.project.planb.feature.service.ConsultingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name="지출 안내 API", description = "사용자가 오늘 사용한 지출을 알려주는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spends/today")
public class ConsultingController {

    private final ConsultingService consultingService;

    /**
     * 오늘의 지출 안내
     */
    @Operation(summary = "오늘의 지출을 안내하고 등록한 예산과의 분석 및 위험도 체크")
    @GetMapping
    public ResponseEntity<TodaySpendDto> getTodaySpend(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();

        log.info("오늘 지출 조회 memberId: {}", member.getId());

        TodaySpendDto todaySpend = consultingService.getTodaySpend(member);
        return ResponseEntity.ok(todaySpend);
    }
}
