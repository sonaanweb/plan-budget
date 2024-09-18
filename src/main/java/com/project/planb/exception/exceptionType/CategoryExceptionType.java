package com.project.planb.exception.exceptionType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CategoryExceptionType implements ExceptionType {

    DUPLICATED_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다.");

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
