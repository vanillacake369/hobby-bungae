package com.example.hobbybungae.domain.post.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.state.entity.State;
import java.util.List;


public record PostRequestDto(
	String title,
	String content,
	State state,
	List<Hobby> hobbies
) {

}
