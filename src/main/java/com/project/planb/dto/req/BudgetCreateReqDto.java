package com.project.planb.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BudgetCreateReqDto(
        @NotNull(message = "카테고리를 지정해주세요")
        Long categoryId,
        @NotNull(message = "예산 총액을 입력해주세요.")
        @Min(value = 0, message = "0 이상이어야 합니다.")
        Integer amount,
        @NotNull(message = "연도를 입력해주세요")
        int year,
        @NotNull(message = "달을 입력해주세요")
        int month
){
}
