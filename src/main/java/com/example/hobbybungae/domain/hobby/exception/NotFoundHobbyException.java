package com.example.hobbybungae.domain.hobby.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 선택한 취미 카테고리가 없는 경우 */
public class NotFoundHobbyException extends DomainException {

	public NotFoundHobbyException(String field, String value, String reason) {
		super(ErrorCode.NOT_FOUND_HOBBY, new ErrorDetail(field, value, reason));
	}

	public NotFoundHobbyException(String field, String value) {
		super(ErrorCode.NOT_FOUND_HOBBY, new ErrorDetail(field, value));
	}

	public NotFoundHobbyException() {
		super(ErrorCode.NOT_FOUND_HOBBY, new ErrorDetail());
	}
}
