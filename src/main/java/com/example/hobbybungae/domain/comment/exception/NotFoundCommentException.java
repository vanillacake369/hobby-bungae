package com.example.hobbybungae.domain.comment.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class NotFoundCommentException extends DomainException {
    public NotFoundCommentException(String field, String value, String reason) {
        super(ErrorCode.NOT_FOUND_COMMENT, new ErrorDetail(field, value, reason));
    }
}
