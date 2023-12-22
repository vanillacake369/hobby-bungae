package com.example.hobbybungae.global.exception;

import com.example.hobbybungae.domain.common.DomainException;

public class AuthenticationFailException extends DomainException {

	public AuthenticationFailException(String field, String value, String reason) {
		super(ErrorCode.ACCESS_DENIED, new ErrorDetail(field, value, reason));
	}
}