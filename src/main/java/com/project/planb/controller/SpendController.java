package com.project.planb.controller;

import com.project.planb.dto.req.SpendReqDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.SpendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/spends")
@RequiredArgsConstructor
public class SpendController {

    private final SpendService spendService;

    // 지출 등록
    @PostMapping
    public ResponseEntity<SpendReqDto> createSpend(
            @RequestBody @Valid SpendReqDto spendReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();

        log.info("지출 생성 memberId: {}", member.getId());

        SpendReqDto createdSpend = spendService.createSpend(spendReqDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpend);

        /* todo : 201, 응답 dto 따로 생성 안 하고 확인 확인 할 정보는 log로 찍어보기, 차이점 정리할 것 */
    }

    /*
    지출 조회는 `기간별 조회가 베이스이다.` | 기간을 선택 안 했을 때 전체 조회도 고려 => 프론트단 생각해보기
    조회된 모든 기간의 지출 합계와 카테고리 별 지출 합계를 같이 반환
    특정 카테고리만으로도 조회할 수 있다
    최소, 최대 금액으로 조회
    합계제외 처리한 지출은 목록에 포함되지만, 모든 지출 합계에서는 제외처리
    */

    // 지출 수정
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