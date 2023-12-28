package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 중복회원 존재 */
public class DuplicatedNickNameException extends DomainException {

	public DuplicatedNickNameException(String field, String value, String reason) {
		super(ErrorCode.DUPLICATED_NICK_NAME, new ErrorDetail(field, value, reason));
	}

	public DuplicatedNickNameException(String field, String value) {
		super(ErrorCode.DUPLICATED_NICK_NAME, new ErrorDetail(field, value));
	}
}
