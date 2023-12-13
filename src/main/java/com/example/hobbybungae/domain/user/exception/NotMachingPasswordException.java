package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 프로필 유저 & 로그인 유저 일치 검증 오류 */
public class NotMachingPasswordException extends DomainException {

	public NotMachingPasswordException(String field, String value, String reason) {
		super(ErrorCode.NOT_MATCHING_PASSWORD, new ErrorDetail(field, value, reason));
	}

	public NotMachingPasswordException(String field, String value) {
		super(ErrorCode.NOT_MATCHING_PASSWORD, new ErrorDetail(field, value));
	}
}
