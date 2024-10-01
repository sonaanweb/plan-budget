package com.project.planb.domain.spend.controller;

import com.project.planb.domain.spend.dto.req.SpendReqDto;
import com.project.planb.domain.spend.dto.res.SpendDetailDto;
import com.project.planb.domain.spend.dto.res.SpendResDto;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.common.security.details.PrincipalDetails;
import com.project.planb.domain.spend.service.SpendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Tag(name = "지출 API", description = "사용자 지출 CRUD")
@RestController
@RequestMapping("/api/spends")
@RequiredArgsConstructor
public class SpendController {

    private final SpendService spendService;

    // 지출 등록
    @Operation(summary = "카테고리별 지출을 등록합니다")
    @PostMapping
    public ResponseEntity<SpendReqDto> createSpend(
            @RequestBody @Valid SpendReqDto spendReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();

        log.info("지출 생성 memberId: {}", member.getId());

        SpendReqDto createdSpend = spendService.createSpend(spendReqDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpend);
    }

    /*
    지출 조회는 `기간별 조회가 베이스이다.`
    조회된 모든 기간의 지출 합계와 카테고리 별 지출 합계를 같이 반환
    특정 카테고리만으로도 조회할 수 있다
    최소, 최대 금액으로 조회
    합계제외 처리한 지출은 목록에 포함되지만, 모든 지출 합계에서는 제외처리
    */
    // 지출 목록 조회
    @Operation(summary = "지출 목록 조회")
    @GetMapping
    public ResponseEntity<SpendResDto> getSpends(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer minAmount,
            @RequestParam(required = false) Integer maxAmount) {

        // 기본값 설정 = null 일 경우
        if (startDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1); // 현재 달의 1일
        }

        if (endDate == null) {
            endDate = LocalDate.now(); // 현재 날짜
        }

        // 유효성 검사
        if (startDate.isAfter(endDate)) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "시작일은 종료일 이전이어야 합니다.");
        }

        if (minAmount != null && maxAmount != null && maxAmount < minAmount) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "최소 금액은 최대 금액보다 작아야 합니다.");
        }

        Member member = principalDetails.getMember();

        log.info("지출 조회 memberId: {}", member.getId());

        SpendResDto spends = spendService.getSpends(member, startDate, endDate, categoryId, minAmount, maxAmount);
        return ResponseEntity.ok(spends);
    }

    // 지출 상세 조회
    @Operation(summary = "등록되어있는 지출 상세보기")
    @GetMapping("/{spendId}")
    public ResponseEntity<SpendDetailDto> getSpendDetail(
            @PathVariable("spendId") Long spendId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        SpendDetailDto spendDetail = spendService.getSpendDetail(member, spendId);
        return ResponseEntity.ok(spendDetail);
    }


    // 지출 수정
    @Operation(summary = "등록되어있는 지출 수정")
    @PatchMapping("/{spendId}")
    public ResponseEntity<SpendReqDto> updateSpend(
            @PathVariable("spendId") Long spendId,
            @RequestBody @Valid SpendReqDto spendReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();

        log.info("지출 수정 memberId: {}, spendId: {}", member.getId(), spendId);

        SpendReqDto updatedSpend = spendService.updateSpend(spendId, spendReqDto, member);
        return ResponseEntity.ok(updatedSpend);
    }

    // 지출 삭제
    @Operation(summary = "등록되어있는 지출 삭제")
    @DeleteMapping("/{spendId}")
    public ResponseEntity<Void> deleteSpend(
            @PathVariable("spendId") Long spendId,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();

        log.info("지출 삭제 memberId: {}, spendId: {}", member.getId(), spendId);

        spendService.deleteSpend(spendId, member);
        return ResponseEntity.noContent().build();
    }
}