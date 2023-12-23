package com.example.hobbybungae.domain.post.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.state.entity.State;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Size;
import java.util.List;

@JsonDeserialize
public record PostUpdateRequestDto(
	@Size(max = 500)
	String title,
	@Size(max = 5000)
	String content,
	State state,
	List<Hobby> hobbies
) {

}
