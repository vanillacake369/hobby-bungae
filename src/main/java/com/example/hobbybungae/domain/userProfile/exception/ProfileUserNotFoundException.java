package com.example.hobbybungae.domain.userProfile.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

public class ProfileUserNotFoundException extends DomainException {

	public ProfileUserNotFoundException(String field, String value) {
		super(ErrorCode.NOT_FOUND_PROFILE_USER, new ErrorDetail(field, value));
	}
}
