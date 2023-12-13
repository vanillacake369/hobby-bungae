package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 프로필 유저 & 로그인 유저 일치 검증 오류 */
public class NotFoundUserException extends DomainException {

	public NotFoundUserException(String field, String value, String reason) {
		super(ErrorCode.NOT_FOUND_USER, new ErrorDetail(field, value, reason));
	}

	public NotFoundUserException(String field, String value) {
		super(ErrorCode.NOT_FOUND_USER, new ErrorDetail(field, value));
	}

	public NotFoundUserException() {
		super(ErrorCode.NOT_FOUND_USER, new ErrorDetail());
	}
}
