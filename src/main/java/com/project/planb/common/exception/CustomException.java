package com.project.planb.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    public CustomException(ErrorCode e) {
        super(e.getMessage());
        this.errorCode = e;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message); // 메시지를 String으로 전달
        this.errorCode = errorCode;
    }
}
