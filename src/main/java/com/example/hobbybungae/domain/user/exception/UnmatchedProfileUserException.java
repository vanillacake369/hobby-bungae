package com.example.hobbybungae.domain.user.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 프로필 유저 & 로그인 유저 일치 검증 오류 */
public class UnmatchedProfileUserException extends DomainException {
    public UnmatchedProfileUserException(String field, String value, String reason) {
        super(ErrorCode.UNMATCHED_PROFILE_USER, new ErrorDetail(field, value, reason));
    }
}
