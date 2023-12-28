package com.example.hobbybungae.domain.post.unit;

public record PostOrderByRequest(
	String orderCondition,
	boolean isAsc
) {

}
