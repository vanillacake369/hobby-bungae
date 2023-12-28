package com.example.hobbybungae.domain.common;

import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

	private final ErrorCode errorCode;
	private final ErrorDetail errorDetail;

	public DomainException(ErrorCode errorCode, ErrorDetail errorDetail) {
		this.errorCode = errorCode;
		this.errorDetail = errorDetail;
	}
}
