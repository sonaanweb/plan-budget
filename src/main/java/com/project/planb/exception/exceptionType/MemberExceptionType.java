package com.project.planb.exception.exceptionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberExceptionType implements ExceptionType{

    // 409 error
    DUPLICATED_ACCOUNT(HttpStatus.CONFLICT, "이미 사용중인 계정입니다.");

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
