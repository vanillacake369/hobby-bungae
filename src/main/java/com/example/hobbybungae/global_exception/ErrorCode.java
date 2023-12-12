package com.example.hobbybungae.global_exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode {
	/* USER */
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 필요한 요청입니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 요청입니다."),
	INVALID_PARAM(HttpStatus.BAD_REQUEST, "잘못된 형식의 입력값입니다."),
	DUPLICATED_ID_NAME(HttpStatus.BAD_REQUEST, "동일한 아이디의 중복회원 존재합니다."),
	DUPLICATED_NICK_NAME(HttpStatus.BAD_REQUEST, "동일한 닉네임의 중복회원 존재합니다."),
	PASSWORD_CONFIRM_FAIL(HttpStatus.BAD_REQUEST, "재입력하신 비밀번호와 패스워드가 일치하지 않습니다."),
	NICKNAME_IN_PASSWORD(HttpStatus.BAD_REQUEST, "재입력하신 비밀번호와 패스워드가 일치하지 않습니다."),
	NOT_MATCHING_PASSWORD_RECONFIRM(HttpStatus.BAD_REQUEST, "비밀번호입력값과 비밀번호 재확인값이 서로 일치하지 않습니다. 다시 입력해주십시오."),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "찾으시는 회원은 존재하지 않습니다."),
	NOT_MATCHING_USER(HttpStatus.NOT_FOUND, "해당 아이템의 소유자가 아니십니다."),
	/* PROFILE */
	NOT_FOUND_PROFILE_USER(HttpStatus.NOT_FOUND, "선택하신 프로필은 존재하지 않는 프로필입니다."),
	UNMATCHED_PROFILE_USER(HttpStatus.UNAUTHORIZED, "프로필의 유저와 사용자가 일치하지 않습니다. "),
	DUPLICATED_HOBBY(HttpStatus.CONFLICT, "이미 존재하는 취미입니다."),
	/* POST */
	NOT_FOUND_HOBBY(HttpStatus.NOT_FOUND, "선택하신 취미는 존재하지 않는 취미입니다."),
	NOT_FOUND_STATE(HttpStatus.NOT_FOUND, "선택하신 지역은 존재하지 않는 지역입니다."),
	NOT_FOUND_POST(HttpStatus.NOT_FOUND, "선택하신 번개글은 존재하지 않는 번개글입니다."),
	INVALID_POST_MODIFIER(HttpStatus.NOT_FOUND, "수정하시려는 번개글의 수정 권한이 없습니다."),
	/* COMMENT */
	NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "선택하신 댓글은 존재하지 않는 댓글입니다."),
	INVALID_COMMENT_MODIFIER(HttpStatus.NOT_FOUND, "수정하시려는 댓글의 수정 권한이 없습니다."),
	UNMATCHED_COMMENT_POST(HttpStatus.CONFLICT, "작성하신 댓글은 해당 게시글의 댓글이 아닙니다.");

	private final HttpStatus code;

	private final String message;

	ErrorCode(HttpStatus code, String message) {
		this.code = code;
		this.message = message;
	}
}
