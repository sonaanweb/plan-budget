package com.project.planb.dto.req;

import org.hibernate.validator.constraints.Range;

public record BudgetFilterReqDto(

        /**
         * 년 - 월로 설정 예산 조회
         */
        Integer year,

        @Range(min = 1, max = 12, message = "1~12월 내로 입력해주세요")
        Integer month
) {
}
