package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 중복회원 존재 */
public class DuplicatedUserException extends DomainException {
    public DuplicatedUserException(String field, String value, String reason) {
        super(ErrorCode.DUPLICATED_USER, new ErrorDetail(field, value, reason));
    }
}
