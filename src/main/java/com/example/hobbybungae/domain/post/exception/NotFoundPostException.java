package com.example.hobbybungae.domain.post.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 번개글이 없는 경우 */
public class NotFoundPostException extends DomainException {

	public NotFoundPostException(String field, String value, String reason) {
		super(ErrorCode.NOT_FOUND_POST, new ErrorDetail(field, value, reason));
	}

	public NotFoundPostException(String field, String value) {
		super(ErrorCode.NOT_FOUND_POST, new ErrorDetail(field, value));
	}
}
