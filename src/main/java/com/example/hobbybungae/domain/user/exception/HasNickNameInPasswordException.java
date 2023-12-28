package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 중복회원 존재 */
public class HasNickNameInPasswordException extends DomainException {

	public HasNickNameInPasswordException(String field, String value, String reason) {
		super(ErrorCode.NICKNAME_IN_PASSWORD, new ErrorDetail(field, value, reason));
	}

	public HasNickNameInPasswordException(String field, String value) {
		super(ErrorCode.NICKNAME_IN_PASSWORD, new ErrorDetail(field, value));
	}
}
