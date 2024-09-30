package com.project.planb.domain.budget.controller;

import com.project.planb.domain.budget.dto.req.BudgetCreateReqDto;
import com.project.planb.domain.budget.dto.req.BudgetPeriodReqDto;
import com.project.planb.domain.budget.dto.res.BudgetCreateResDto;
import com.project.planb.domain.budget.dto.res.BudgetResDto;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.common.security.PrincipalDetails;
import com.project.planb.domain.budget.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="예산 API", description = "예산을 등록하고 등록된 예산 리스트를 조회하는 API")
@Slf4j
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // 예산 등록 year & month
    @Operation(summary = "년/월을 지정하여 카테고리별 예산 등록")
    @PostMapping
    public ResponseEntity<BudgetCreateResDto> createBudget(
            @RequestBody @Valid BudgetCreateReqDto budgetCreateReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();  // memberId만 사용
        log.info("예산 생성 memberId: {}", member);

        BudgetCreateResDto budgetCreateResDto = budgetService.createBudget(budgetCreateReqDto, member);
        return ResponseEntity.ok(budgetCreateResDto);
    }

    // 등록한 예산 리스트 조회 (년/월 필터링)
    @Operation(summary = "사용자가 등록한 예산 리스트 조회")
    @GetMapping
    public ResponseEntity<BudgetResDto> getBudgets(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        log.info("예산 조회 요청 memberId: {}, year: {}, month: {}", member, year, month);

        BudgetPeriodReqDto filterReqDto = new BudgetPeriodReqDto(year, month);
        BudgetResDto budgetResponse = budgetService.getBudgetsByMemberAndDate(member, filterReqDto);
        return ResponseEntity.ok(budgetResponse);
    }
}