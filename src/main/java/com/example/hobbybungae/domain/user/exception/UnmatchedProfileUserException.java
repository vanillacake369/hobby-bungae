package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class UnmatchedProfileUserException extends DomainException {
    public UnmatchedProfileUserException(String field, String value, String reason) {
        super(ErrorCode.UNMATCHED_PROFILE_USER, new ErrorDetail(field, value, reason));
    }
}
