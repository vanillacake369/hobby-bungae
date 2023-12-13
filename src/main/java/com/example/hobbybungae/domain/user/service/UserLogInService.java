package com.example.hobbybungae.domain.user.service;

import com.example.hobbybungae.domain.user.dto.request.UserLogInRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.NotFoundUserException;
import com.example.hobbybungae.domain.user.exception.NotMachingPasswordException;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLogInService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	public ResponseEntity<UserResponseDto> login(UserLogInRequestDto requestDto) {
		// 회원 미존재 에러
		User user = getUserByIdName(requestDto);

		// 패스워드 불일치 에러
		verifyMatchingPassword(requestDto, user);

		// {JWT -> 헤더} && 성공 반환
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.AUTHORIZATION, jwtUtil.createToken(requestDto.idName()));
		return ResponseEntity.ok()
			.headers(responseHeaders)
			.body(new UserResponseDto(user.getIdName(), user.getName()));
	}

	void verifyMatchingPassword(UserLogInRequestDto requestDto, User user) {
		if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
			throw new NotMachingPasswordException("password", requestDto.password());
		}
	}

	User getUserByIdName(UserLogInRequestDto requestDto) {
		return userRepository.findByIdName(requestDto.idName()).orElseThrow(() ->
			new NotFoundUserException("idName", requestDto.idName())
		);
	}
}
