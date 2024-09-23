package com.project.planb.controller;

import com.project.planb.dto.req.BudgetCreateReqDto;
import com.project.planb.dto.res.BudgetResDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    // 예산 등록 year & month
    @PostMapping
    public ResponseEntity<BudgetResDto> createBudget(
            @RequestBody @Valid BudgetCreateReqDto budgetCreateReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember(); // Member 객체
        log.info("예산 생성 memberId: {}", member.getId());

        BudgetResDto budgetResDto = budgetService.createBudget(budgetCreateReqDto, member);
        return ResponseEntity.ok(budgetResDto);
    }

    // 등록한 예산 리스트 조회
    @GetMapping
    public ResponseEntity<List<BudgetResDto>> getBudgets(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        log.info("예산 조회 memberId: {}", member.getId());

        List<BudgetResDto> budgets = budgetService.getBudgetsByMember(member);
        return ResponseEntity.ok(budgets);
    }
}
