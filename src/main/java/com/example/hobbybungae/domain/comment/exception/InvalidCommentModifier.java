package com.example.hobbybungae.domain.comment.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.global.exception.ErrorDetail;

/* 댓글유저 & 로그인 유저 일치 검증 오류  */
public class InvalidCommentModifier extends DomainException {

	public InvalidCommentModifier(String field, String value) {
		super(ErrorCode.INVALID_COMMENT_MODIFIER, new ErrorDetail(field, value));
	}
}
