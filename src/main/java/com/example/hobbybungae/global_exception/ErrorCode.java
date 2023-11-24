package com.example.hobbybungae.global_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 요청입니다."),
    INVALID_PARAM(HttpStatus.BAD_REQUEST, "잘못된 형식의 입력값입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 필요한 요청입니다."),
    DUPLICATED_USER(HttpStatus.CONFLICT, "동일한 아이디의 중복회원 존재합니다.");
    private final HttpStatus code;
    private final String message;

    ErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
