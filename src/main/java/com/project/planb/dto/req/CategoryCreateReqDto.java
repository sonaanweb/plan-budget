package com.project.planb.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateReqDto(
        // 카테고리 생성 요청 dto
        @NotBlank(message = "필수 입력값 입니다.")
        @Size(min = 1, max = 10, message = "10자 이내로 작성해주세요.")
        String categoryName
) {
}
