package com.example.hobbybungae.global_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorCode {
    private final HttpStatus code;
    private final String message;

    public ErrorCode(final HttpStatus code, final String message) {
        this.code = code;
        this.message = message;
    }
}
