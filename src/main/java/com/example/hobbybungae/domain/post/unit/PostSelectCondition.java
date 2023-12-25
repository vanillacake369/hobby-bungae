package com.example.hobbybungae.domain.post.unit;

import com.example.hobbybungae.domain.state.entity.State;
import lombok.Builder;

@Builder
public record PostSelectCondition(
	State state,
	Long userId,
	String title,
	String contents
) {

}
