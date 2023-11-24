package com.example.hobbybungae.domain.post.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class InvalidPostModifierException extends DomainException {
    public InvalidPostModifierException(String field, String value, String reason) {
        super(ErrorCode.INVALID_POST_MODIFIER, new ErrorDetail(field, value, reason));
    }
}
