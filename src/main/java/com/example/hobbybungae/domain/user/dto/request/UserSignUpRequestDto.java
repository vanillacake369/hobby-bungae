package com.example.hobbybungae.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserSignUpRequestDto(
	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z0-9]*$") // 특수문자 X
	@Size(min = 5, max = 16)
	String idName,
	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z]*$")
	String name,

	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z]*$")
	@Size(min = 3)
	String nickName,

	@NotBlank
	@Email
	String email,

	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z0-9\\S]*$") // 특수문자 O
	@Size(min = 8, max = 32)
	String password,

	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z0-9\\S]*$") // 특수문자 O
	@Size(min = 8, max = 32)
	String passwordReconfirm,

	@Size(max = 300)
	String introduction
) {

}
