package com.example.hobbybungae.domain.user.dto.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record UserResponseDto(
	String idName,
	String name) {

}
