package com.example.hobbybungae.domain.hobby.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import lombok.Getter;

@Getter
public class HobbyResponseDto {

	private Long hobbyId;

	private String hobbyName;

	public HobbyResponseDto(Hobby hobby) {
		this.hobbyId = hobby.getId();
		this.hobbyName = hobby.getHobbyName();
	}
}
