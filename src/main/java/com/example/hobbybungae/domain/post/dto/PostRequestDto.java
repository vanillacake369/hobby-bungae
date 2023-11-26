package com.example.hobbybungae.domain.post.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.state.entity.State;
import java.util.List;
import lombok.Getter;


@Getter
public class PostRequestDto {

	private String title;

	private String content;

	private State state;

	private String author;

	private List<Hobby> hobbies;
}
