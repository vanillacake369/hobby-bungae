package com.example.hobbybungae.global_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode {
    UNAUTHORIZED(new ErrorCodeFormat(HttpStatus.UNAUTHORIZED, "로그인이 필요한 요청입니다.")),
    INVALID_PARAM(new ErrorCodeFormat(HttpStatus.BAD_REQUEST, "잘못된 형식의 입력값입니다."));

    private final ErrorCodeFormat errorCodeFormat;

    GlobalErrorCode(ErrorCodeFormat errorCodeFormat) {
        this.errorCodeFormat = errorCodeFormat;
    }

    public HttpStatus getStatusCode() {
        return this.errorCodeFormat.getCode();
    }
}
