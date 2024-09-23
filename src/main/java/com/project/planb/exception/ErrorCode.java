package com.project.planb.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /* General Exception 500 */
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값입니다."),

    /* Member Exception */
    // 409 error
    DUPLICATED_ACCOUNT(HttpStatus.CONFLICT, "이미 사용중인 계정입니다."),
    // 404 error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    // 401 error
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"권한이 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패: 계정 또는 비밀번호가 잘못되었습니다."),

    /* Token Exception */
    // 401 error
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    /* Category Exception */
    // 404 error
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),

    /* Spend Exception */
    SPEND_NOT_FOUND(HttpStatus.NOT_FOUND, "지출 정보가 존재하지 않습니다.");


    private final HttpStatus status;
    private final String message;
}
