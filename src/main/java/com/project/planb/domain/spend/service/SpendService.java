package com.project.planb.domain.spend.service;

import com.project.planb.domain.spend.dto.req.SpendReqDto;
import com.project.planb.domain.spend.dto.res.SpendDetailDto;
import com.project.planb.domain.spend.dto.res.SpendResDto;
import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.spend.entity.Spend;
import com.project.planb.common.exception.CustomException;
import com.project.planb.common.exception.ErrorCode;
import com.project.planb.domain.category.repository.CategoryRepository;
import com.project.planb.domain.spend.repository.SpendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;
    private final CategoryRepository categoryRepository;

    // 지출 생성
    @Transactional
    public SpendReqDto createSpend(SpendReqDto spendReqDto, Member member) {

        Category category = categoryRepository.findById(spendReqDto.categoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // Spend 엔티티 생성 toEntity
        Spend spend = spendReqDto.toEntity(member, category);

        spendRepository.save(spend);

        return new SpendReqDto(
                category.getId(),
                spend.getAmount(),
                spend.getMemo(),
                spend.getSpendAt(),
                spend.getIsExcludedSum()
        );
    }

    // 지출 수정
    @Transactional
    public SpendReqDto updateSpend(Long spendId, SpendReqDto spendReqDto, Member member) {
        Spend spend = spendRepository.findById(spendId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPEND_NOT_FOUND));

        // memberId를 가져와 작성자 예외처리
        if (!spend.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        Category category = categoryRepository.findById(spendReqDto.categoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        spend.spendUpdate(spendReqDto.spendAt(), category, spendReqDto.amount(),
                spendReqDto.memo(), spendReqDto.isExcludedSum());

        spendRepository.save(spend);

        return new SpendReqDto(
                category.getId(),
                spend.getAmount(),
                spend.getMemo(),
                spend.getSpendAt(),
                spend.getIsExcludedSum()
        );
    }

    // 지출 삭제
    @Transactional
    public void deleteSpend(Long spendId, Member member) {
        Spend spend = spendRepository.findById(spendId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPEND_NOT_FOUND));

        if (!spend.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        spendRepository.delete(spend);
    }


    // 지출 목록 조회 메서드
    public SpendResDto getSpends(Member member, LocalDate startDate, LocalDate endDate, Long categoryId, Integer minAmount, Integer maxAmount) {
        List<Spend> spends = spendRepository.searchSpends(member.getId(), startDate, endDate, categoryId, minAmount, maxAmount, null);

        Integer totalAmount = spends.stream()
                .filter(spend -> !spend.getIsExcludedSum()) // 합계 제외 필터링
                .mapToInt(Spend::getAmount)
                .sum();

        // 카테고리별 지출 총액 계산
        Map<Long, Integer> categoryAmountsMap = spends.stream()
                .collect(Collectors.groupingBy(spend -> spend.getCategory().getId(),
                        Collectors.summingInt(Spend::getAmount)));

        // SpendDetail 리스트로 변환
        List<SpendResDto.SpendList> spendDetails = spends.stream()
                .map(spend -> new SpendResDto.SpendList(
                        spend.getId(),
                        spend.getSpendAt(),
                        spend.getCategory().getId(),
                        spend.getAmount(),
                        spend.getMemo(),
                        spend.getIsExcludedSum()
                ))
                .toList();

        return new SpendResDto(totalAmount, categoryAmountsMap, spendDetails);
    }

    // 지출 상세
    public SpendDetailDto getSpendDetail(Member member, Long spendId) {
        Spend spend = spendRepository.findByIdAndMemberId(spendId, member.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.SPEND_NOT_FOUND));

        if (!spend.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        return new SpendDetailDto(
                spend.getId(),
                spend.getSpendAt(),
                spend.getCategory().getId(),
                spend.getCategory().getCategoryName(),
                spend.getAmount(),
                spend.getMemo(),
                spend.getIsExcludedSum()
        );
    }
}
