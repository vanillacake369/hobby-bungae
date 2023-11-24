package com.example.hobbybungae.domain.comment.exception;

import com.example.hobbybungae.domain.common.DomainException;
import com.example.hobbybungae.global_exception.ErrorCode;
import com.example.hobbybungae.global_exception.ErrorDetail;

/* 댓글이 해당 게시글의 댓글이 아닌 경우 */
public class UnmatchedCommentPost extends DomainException {
    public UnmatchedCommentPost(String field, String value, String reason) {
        super(ErrorCode.UNMATCHED_COMMENT_POST, new ErrorDetail(field, value, reason));
    }

    public UnmatchedCommentPost(String field, String value) {
        super(ErrorCode.UNMATCHED_COMMENT_POST, new ErrorDetail(field, value));
    }
}
