package com.example.hobbybungae.domain.user.service;

import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.request.VerifyNicknameRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.dto.response.VerifyNicknameResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.DuplicatedIdNameException;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserValidator userValidator;

	public ResponseEntity<UserResponseDto> signUp(UserSignUpRequestDto requestDto) throws DuplicatedIdNameException {
		userValidator.verifyDuplicatedUser(requestDto);
		userValidator.validatePasswordReconfirmation(requestDto.password(), requestDto.passwordReconfirm());

		User newUser = User.builder()
			.idName(requestDto.idName())
			.name(requestDto.name())
			.email(requestDto.email())
			.nickName(requestDto.nickName())
			.password(passwordEncoder.encode(requestDto.password()))
			.introduction(requestDto.introduction())
			.build();
		userRepository.save(newUser);

		return new ResponseEntity<>(new UserResponseDto(newUser.getIdName(), newUser.getName()), HttpStatus.CREATED);
	}

	public ResponseEntity<VerifyNicknameResponseDto> verifyNicknameDuplication(VerifyNicknameRequestDto requestDto) {
		userValidator.verifyDuplicatedNickName(requestDto.nickName());

		return new ResponseEntity<>(new VerifyNicknameResponseDto(requestDto.nickName(), true), HttpStatus.OK);
	}
}
