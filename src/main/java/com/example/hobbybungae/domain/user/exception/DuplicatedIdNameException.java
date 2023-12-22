package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 중복회원 존재 */
public class DuplicatedIdNameException extends DomainException {

	public DuplicatedIdNameException(String field, String value, String reason) {
		super(ErrorCode.DUPLICATED_ID_NAME, new ErrorDetail(field, value, reason));
	}

	public DuplicatedIdNameException(String field, String value) {
		super(ErrorCode.DUPLICATED_ID_NAME, new ErrorDetail(field, value));
	}
}
