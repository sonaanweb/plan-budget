package com.project.planb.controller;

import com.project.planb.dto.req.BudgetCreateReqDto;
import com.project.planb.dto.req.BudgetFilterReqDto;
import com.project.planb.dto.res.BudgetCreateResDto;
import com.project.planb.dto.res.BudgetResDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        Member member = principalDetails.getMember();
        log.info("예산 생성 memberId: {}", member.getId());

        BudgetCreateResDto budgetCreateResDto = budgetService.createBudget(budgetCreateReqDto, member);
        return ResponseEntity.ok(budgetCreateResDto);
    }

    /* 등록한 예산 리스트 조회
    @Operation(summary = "사용자가 등록한 예산 리스트 조회")
    @GetMapping
    public ResponseEntity<List<BudgetResDto>> getBudgets(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        log.info("예산 조회 memberId: {}", member.getId());

        List<BudgetResDto> budgets = budgetService.getBudgetsByMember(member);
        return ResponseEntity.ok(budgets);
    }
     */

    // 등록한 예산 리스트 조회 (년/월 필터링)
    @Operation(summary = "사용자가 등록한 예산 리스트 조회")
    @GetMapping
    public ResponseEntity<BudgetResDto> getBudgets(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        log.info("예산 조회 요청 memberId: {}, year: {}, month: {}", member.getId(), year, month);

        BudgetFilterReqDto filterReqDto = new BudgetFilterReqDto(year, month);
        BudgetResDto budgetResponse = budgetService.getBudgetsByMemberAndDate(member, filterReqDto);
        return ResponseEntity.ok(budgetResponse);
    }
}