package com.example.hobbybungae.domain.user.controller;

import com.example.hobbybungae.domain.user.dto.request.UserLogInRequestDto;
import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.request.VerifyNicknameRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.dto.response.VerifyNicknameResponseDto;
import com.example.hobbybungae.domain.user.service.UserLogInService;
import com.example.hobbybungae.domain.user.service.UserSignUpService;
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

	private final UserSignUpService userSignUpService;
	private final UserLogInService userLogInService;

	@PostMapping
	public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {
		return userSignUpService.signUp(requestDto);
	}

	@PostMapping("/verify/nickname")
	public ResponseEntity<VerifyNicknameResponseDto> verifyNicknameDuplication(@RequestBody @Valid VerifyNicknameRequestDto requestDto) {
		return userSignUpService.verifyNicknameDuplication(requestDto);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserLogInRequestDto userLogInRequestDto) {
		return userLogInService.login(userLogInRequestDto);
	}
}
