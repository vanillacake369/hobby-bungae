package com.example.hobbybungae.domain.user.service;

import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.request.VerifyNicknameRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.dto.response.VerifyNicknameResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.DuplicatedIdNameException;
import com.example.hobbybungae.domain.user.exception.DuplicatedNickNameException;
import com.example.hobbybungae.domain.user.exception.HasNickNameInPasswordException;
import com.example.hobbybungae.domain.user.exception.NotMachingPasswordReconfirm;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<UserResponseDto> signUp(UserSignUpRequestDto requestDto) throws DuplicatedIdNameException {
		verifyDuplicatedUser(requestDto);
		validatePasswordReconfirmation(requestDto.password(), requestDto.passwordReconfirm());

		User newUser = User.builder()
			.idName(requestDto.idName())
			.name(requestDto.name())
			.password(passwordEncoder.encode(requestDto.password()))
			.build();
		userRepository.save(newUser);

		return new ResponseEntity<>(new UserResponseDto(newUser.getIdName(), newUser.getName()), HttpStatus.OK);
	}

	public ResponseEntity<VerifyNicknameResponseDto> verifyNicknameDuplication(VerifyNicknameRequestDto requestDto) {
		verifyDuplicatedNickName(requestDto.nickName());

		return new ResponseEntity<>(new VerifyNicknameResponseDto(requestDto.nickName(), true), HttpStatus.OK);
	}

	void validatePasswordReconfirmation(String password, String passwordConfirm) {
		if (!password.equals(passwordConfirm)) {
			throw new NotMachingPasswordReconfirm();
		}
	}

	private void verifyDuplicatedUser(UserSignUpRequestDto requestDto) throws DuplicatedIdNameException {
		// idName 중복 케이스
		verifyDuplicatedIdName(requestDto.idName());
		// nickName 중복 케이스
		verifyDuplicatedNickName(requestDto.nickName());
		// nickName 이 password 에 들어간 케이스
		verifyNicknameInPassword(requestDto.password(), requestDto.nickName());
	}

	private void verifyNicknameInPassword(String password, String nickname) {
		if (hasNicknameInPassword(password, nickname)) {
			throw new HasNickNameInPasswordException("password", password);
		}
	}

	private void verifyDuplicatedNickName(String nickName) {
		if (hasDuplicatedNickName(nickName)) {
			throw new DuplicatedNickNameException("nickName", nickName);
		}
	}

	private void verifyDuplicatedIdName(String idName) {
		if (hasDuplicatedIdName(idName)) {
			throw new DuplicatedIdNameException("idName", idName);
		}
	}

	boolean hasNicknameInPassword(String password, String nickName) {
		return password.contains(nickName);
	}

	boolean hasDuplicatedIdName(String idName) {
		return userRepository.findByIdName(idName).isPresent();
	}

	boolean hasDuplicatedNickName(String nickName) {
		return userRepository.findByNickName(nickName).isPresent();
	}
}
