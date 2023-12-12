package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 패스워드 입력값과 패스워드 재확인 입력값이 서로 다른 경우 */
public class NotMachingPasswordReconfirm extends DomainException {

	public NotMachingPasswordReconfirm(String field, String value, String reason) {
		super(ErrorCode.NOT_MATCHING_PASSWORD_RECONFIRM, new ErrorDetail(field, value, reason));
	}

	public NotMachingPasswordReconfirm(String field, String value) {
		super(ErrorCode.NOT_MATCHING_PASSWORD_RECONFIRM, new ErrorDetail(field, value));
	}

	public NotMachingPasswordReconfirm() {
		super(ErrorCode.NOT_MATCHING_PASSWORD_RECONFIRM, new ErrorDetail());
	}
}
