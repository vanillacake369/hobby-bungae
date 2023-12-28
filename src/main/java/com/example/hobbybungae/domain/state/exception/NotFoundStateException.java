package com.example.hobbybungae.domain.state.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 선택한 지역이 없는 경우 */
public class NotFoundStateException extends DomainException {

	public NotFoundStateException(String field, String value, String reason) {
		super(ErrorCode.NOT_FOUND_STATE, new ErrorDetail(field, value, reason));
	}

	public NotFoundStateException(String field, String value) {
		super(ErrorCode.NOT_FOUND_STATE, new ErrorDetail(field, value));
	}

	public NotFoundStateException() {
		super(ErrorCode.NOT_FOUND_STATE, new ErrorDetail());
	}
}
