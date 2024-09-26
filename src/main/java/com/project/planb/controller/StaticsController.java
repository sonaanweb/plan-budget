package com.project.planb.controller;

import com.project.planb.dto.res.StaticsDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.StaticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/statics")
@RequiredArgsConstructor
public class StaticsController {

    private final StaticsService staticsService;

    /**
     * 지출 통계: 지난 달과 비교
     */
    @GetMapping("/monthly")
    public ResponseEntity<StaticsDto> compareWithLastMonth(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        StaticsDto statistics = staticsService.compareWithLastMonth(member);
        return ResponseEntity.ok(statistics);
    }

    /**
     * 지출 통계: 지난 주와 비교
     */
    @GetMapping("/weekly")
    public ResponseEntity<StaticsDto> compareWithLastWeek(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        StaticsDto statistics = staticsService.compareWithLastWeek(member);
        return ResponseEntity.ok(statistics);
    }
}
