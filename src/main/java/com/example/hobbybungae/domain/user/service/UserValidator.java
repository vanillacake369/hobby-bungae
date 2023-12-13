package com.example.hobbybungae.domain.user.service;

import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.exception.DuplicatedIdNameException;
import com.example.hobbybungae.domain.user.exception.DuplicatedNickNameException;
import com.example.hobbybungae.domain.user.exception.HasNickNameInPasswordException;
import com.example.hobbybungae.domain.user.exception.NotMachingPasswordReconfirm;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserValidator {

	private final UserRepository userRepository;


	void validatePasswordReconfirmation(String password, String passwordConfirm) {
		if (!password.equals(passwordConfirm)) {
			throw new NotMachingPasswordReconfirm();
		}
	}

	void verifyDuplicatedUser(UserSignUpRequestDto requestDto) throws DuplicatedIdNameException {
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

	void verifyDuplicatedNickName(String nickName) {
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
