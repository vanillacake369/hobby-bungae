package com.example.hobbybungae.domain.user.service;

import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.DuplicatedUserException;
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

	private static final String DUPLICATED_USER_ERROR_MESSAGE = "중복되지 않는 아이디를 확인해주시길 바랍니다.";

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<UserResponseDto> signUp(UserSignUpRequestDto requestDto) throws DuplicatedUserException {
		verifyDuplicatedUser(requestDto);

		User newUser = User.builder()
			.idName(requestDto.idName())
			.name(requestDto.name())
			.password(passwordEncoder.encode(requestDto.password()))
			.build();
		userRepository.save(newUser);

		return new ResponseEntity<>(new UserResponseDto(newUser.getIdName(), newUser.getName()), HttpStatus.OK);
	}

	private void verifyDuplicatedUser(UserSignUpRequestDto requestDto) throws DuplicatedUserException {
		if (hasDuplicatedIdName(requestDto.idName())
			&& hasDuplicatedNickName(requestDto.nickName())
			&& hasNicknameInPassword(requestDto.password(), requestDto.nickName())
		) {
			throw new DuplicatedUserException("id_name", requestDto.idName(), DUPLICATED_USER_ERROR_MESSAGE);
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
