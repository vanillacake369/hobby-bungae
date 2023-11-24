package com.example.hobbybungae.domain.hobby.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class DuplicatedHobbyException extends DomainException {
    public DuplicatedHobbyException(String field, String value, String reason) {
        super(ErrorCode.DUPLICATED_HOBBY, new ErrorDetail(field, value, reason));
    }
}
