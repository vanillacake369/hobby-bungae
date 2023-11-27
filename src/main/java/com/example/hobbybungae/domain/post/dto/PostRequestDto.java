package com.example.hobbybungae.domain.post.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.state.entity.State;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonDeserialize
public record PostRequestDto(
	String title,
	String content,
	State state,
	List<Hobby> hobbies
) {

}
