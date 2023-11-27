package com.example.hobbybungae.domain.userProfile.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

public class NotMatchingIdException extends DomainException {

	public NotMatchingIdException(String field, String value, String reason) {
		super(ErrorCode.NOT_MATCHING_USER, new ErrorDetail(field, value, reason));
	}

	public NotMatchingIdException(String field, String value) {
		super(ErrorCode.NOT_MATCHING_USER, new ErrorDetail(field, value));
	}
}