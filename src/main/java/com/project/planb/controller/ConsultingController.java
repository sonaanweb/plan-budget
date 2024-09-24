package com.project.planb.controller;

import com.project.planb.dto.res.TodaySpendDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spends/today")
public class ConsultingController {

    private final ConsultingService consultingService;

    /*
    오늘의 지출 안내
    */
    @GetMapping
    public ResponseEntity<TodaySpendDto> getTodaySpend(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();

        log.info("오늘 지출 조회 memberId: {}", member.getId());

        TodaySpendDto todaySpend = consultingService.getTodaySpend(member);
        return ResponseEntity.ok(todaySpend);
    }
}
