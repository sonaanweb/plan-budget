package com.project.planb.domain.member.dto.req;

public record MemberLoginReqDto(
        String account,
        String password
) {
}
