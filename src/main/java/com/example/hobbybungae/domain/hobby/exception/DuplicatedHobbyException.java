package com.example.hobbybungae.domain.hobby.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 중복 카테고리 존재  */
public class DuplicatedHobbyException extends DomainException {

	public DuplicatedHobbyException(String field, String value) {
		super(ErrorCode.DUPLICATED_HOBBY, new ErrorDetail(field, value));
	}
}
