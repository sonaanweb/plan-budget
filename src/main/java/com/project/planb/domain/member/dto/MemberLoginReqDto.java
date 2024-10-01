package com.project.planb.domain.member.dto;

public record MemberLoginReqDto(
        String account,
        String password
) {
}
