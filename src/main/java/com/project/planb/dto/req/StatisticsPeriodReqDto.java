package com.project.planb.dto.req;

import org.hibernate.validator.constraints.Range;

public record StatisticsPeriodReqDto(
        /**
         * 기간 별 (월 단위) 사용량 통계 요청 DTO (년 / 월)
         */
        Integer year,

        @Range(min = 1, max = 12, message = "1~12월 내로 입력해주세요")
        Integer month
) {
}
