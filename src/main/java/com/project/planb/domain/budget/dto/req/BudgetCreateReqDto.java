package com.project.planb.domain.budget.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record BudgetCreateReqDto(
        @NotNull(message = "카테고리를 지정해주세요")
        Long categoryId,
        @NotNull(message = "예산 총액을 입력해주세요.")
        @Min(value = 0, message = "0 이상이어야 합니다.")
        Integer amount,
        @NotNull(message = "연도를 입력해주세요")
        Integer year,
        @NotNull(message = "달을 입력해주세요")
        @Range(min = 1, max = 12, message = "달은 1에서 12 사이의 값이어야 합니다.")
        int month
){
}
