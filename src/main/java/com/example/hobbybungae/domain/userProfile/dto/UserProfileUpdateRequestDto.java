package com.example.hobbybungae.domain.userProfile.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import java.util.List;

public record UserProfileUpdateRequestDto(
	String idName,
	String name,
	String passwordReconfirm,
	String password,
	String introduction,
	List<Hobby> hobbies
) {

}