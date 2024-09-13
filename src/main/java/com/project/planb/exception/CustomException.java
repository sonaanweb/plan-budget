package com.project.planb.exception;

import com.project.planb.exception.exceptionType.ExceptionType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionType exceptionType;

    public CustomException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }
}
