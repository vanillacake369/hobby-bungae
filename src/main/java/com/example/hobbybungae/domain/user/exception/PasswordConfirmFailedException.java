package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class PasswordConfirmFailedException extends DomainException {
    public PasswordConfirmFailedException(String field, String value, String reason) {
        super(ErrorCode.PASSWORD_CONFIRM_FAIL, new ErrorDetail(field, value, reason));
    }
}
