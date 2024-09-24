package com.project.planb.service;

import com.project.planb.dto.req.SpendReqDto;
import com.project.planb.dto.res.SpendResDto;
import com.project.planb.entity.Category;
import com.project.planb.entity.Member;
import com.project.planb.entity.Spend;
import com.project.planb.exception.CustomException;
import com.project.planb.exception.ErrorCode;
import com.project.planb.repository.CategoryRepository;
import com.project.planb.repository.SpendQRepository;
import com.project.planb.repository.SpendRepository;
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
    private final SpendQRepository spendQRepository;

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
        // (!spend.getMember().equals(member))일 때는 제대로 구현되지 않았음
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


    // 지출 조회 메서드
    public SpendResDto getSpends(Member member, LocalDate startDate, LocalDate endDate, Long categoryId, Integer minAmount, Integer maxAmount) {
        List<Spend> spends = spendQRepository.searchSpends(member.getId(), startDate, endDate, categoryId, minAmount, maxAmount, null);

        // 총액 계산
        Integer totalAmount = spends.stream().mapToInt(Spend::getAmount).sum();

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
}
