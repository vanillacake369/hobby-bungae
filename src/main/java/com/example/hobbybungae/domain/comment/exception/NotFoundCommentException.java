package com.example.hobbybungae.domain.comment.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 댓글이 없는 경우  */
public class NotFoundCommentException extends DomainException {
    public NotFoundCommentException(String field, String value) {
        super(ErrorCode.NOT_FOUND_COMMENT, new ErrorDetail(field, value));
    }
}
