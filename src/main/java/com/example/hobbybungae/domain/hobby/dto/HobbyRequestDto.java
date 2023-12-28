package com.example.hobbybungae.domain.hobby.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HobbyRequestDto {

	@NotBlank
	@Pattern(regexp = "^[가-힣a-zA-Z]*$")
	private String hobbyName;
}
