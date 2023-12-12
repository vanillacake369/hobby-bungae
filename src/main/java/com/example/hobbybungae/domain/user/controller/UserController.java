package com.example.hobbybungae.domain.user.controller;

import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hobby-bungae/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {
		return userService.signUp(requestDto);
	}
}
