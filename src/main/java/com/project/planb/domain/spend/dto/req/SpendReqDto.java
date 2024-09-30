package com.project.planb.domain.spend.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.planb.domain.category.entity.Category;
import com.project.planb.domain.member.entity.Member;
import com.project.planb.domain.spend.entity.Spend;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record SpendReqDto(

        @NotNull(message = "카테고리를 선택해주세요")
        Long categoryId,

        @NotNull(message = "금액은 필수 입력 사항입니다.")
        @Min(value = 0, message = "0 이상이어야 합니다.")
        Integer amount,

        String memo,

        @NotNull(message = "날짜는 필수 입력 사항입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate spendAt,

        Boolean isExcludedSum // null일 경우 false
) {
    public Spend toEntity(Member member, Category category) {
        return Spend.builder()
                .member(member)
                .spendAt(spendAt)
                .category(category)
                .amount(amount)
                .memo(memo)
                .isExcludedSum(isExcludedSum != null ? isExcludedSum : false)
                .build();
    }
}
