package com.project.planb.exception.exceptionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberExceptionType implements ExceptionType{

    // 409 error
    DUPLICATED_ACCOUNT(HttpStatus.CONFLICT, "이미 사용중인 계정입니다."),

    // 401 error 토큰 관련
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    // 401 error
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패: 계정 또는 비밀번호가 잘못되었습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus status(){
        return this.status;
    }

    @Override
    public String message(){
        return this.message;
    }
}
