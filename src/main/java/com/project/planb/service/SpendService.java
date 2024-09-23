package com.project.planb.service;

import com.project.planb.dto.req.SpendCreateReqDto;
import com.project.planb.entity.Category;
import com.project.planb.entity.Member;
import com.project.planb.entity.Spend;
import com.project.planb.exception.CustomException;
import com.project.planb.exception.ErrorCode;
import com.project.planb.repository.CategoryRepository;
import com.project.planb.repository.SpendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;
    private final CategoryRepository categoryRepository;

    // 지출 생성
    @Transactional
    public SpendCreateReqDto createSpend(SpendCreateReqDto spendCreateReqDto, Member member) {

        Category category = categoryRepository.findById(spendCreateReqDto.categoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        // Spend 엔티티 생성 toEntity
        Spend spend = spendCreateReqDto.toEntity(member, category);

        spendRepository.save(spend);

        return new SpendCreateReqDto(
                category.getId(),
                spend.getAmount(),
                spend.getMemo(),
                spend.getSpendAt(),
                spend.getIsExcludedSum()
        );
    }

    // 리스트 조회 todo: 요구사항 별 추가할 것
    public List<Spend> getSpendsByMember(Member member) {
        return spendRepository.findByMember(member);
    }
}
