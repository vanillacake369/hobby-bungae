package com.example.hobbybungae.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record VerifyNicknameRequestDto(
	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z]*$")
	@Size(min = 3)
	String nickName
) {

}
