package com.project.planb.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // CustomException 예외처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        return buildErrorResponse(exception.getErrorCode().getStatus(), exception.getErrorCode(), exception.getMessage(), exception);
    }

    // valid 예외처리 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT_VALUE, errorMessage, exception);
    }

    // 정의하지 않은 오류 == 서버에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> undefinedException(Exception exception) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN_ERROR, exception.getMessage(), exception);
    }

    // 공통 ErrorResponse 빌드 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, ErrorCode errorCode, String message, Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .build();

        // log.error(message, exception);

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}
