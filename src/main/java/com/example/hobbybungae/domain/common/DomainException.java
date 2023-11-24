package com.example.hobbybungae.domain.common;

import com.example.hobbybungae.global_exception.ErrorCode;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final ErrorCode errorCode;

    public DomainException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DomainException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DomainException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public DomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }
}
