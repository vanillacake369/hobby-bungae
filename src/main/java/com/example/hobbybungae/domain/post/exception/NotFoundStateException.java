package com.example.hobbybungae.domain.post.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class NotFoundStateException extends DomainException {
    public NotFoundStateException(String field, String value, String reason) {
        super(ErrorCode.NOT_FOUND_STATE, new ErrorDetail(field, value, reason));
    }
}
