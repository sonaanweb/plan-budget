package com.project.planb.controller;

import com.project.planb.dto.req.SpendCreateReqDto;
import com.project.planb.entity.Member;
import com.project.planb.security.PrincipalDetails;
import com.project.planb.service.SpendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/spends")
@RequiredArgsConstructor
public class SpendController {

    private final SpendService spendService;

    // 지출 등록
    @PostMapping
    public ResponseEntity<SpendCreateReqDto> createSpend(
            @RequestBody @Valid SpendCreateReqDto spendCreateReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Member member = principalDetails.getMember();
        SpendCreateReqDto createdSpend = spendService.createSpend(spendCreateReqDto, member);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpend); // 201, 응답 dto 따로 생성 안 하고 확인
    }
}