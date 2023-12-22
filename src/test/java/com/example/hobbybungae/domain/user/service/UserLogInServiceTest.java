package com.example.hobbybungae.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.config.WebSecurityConfig;
import com.example.hobbybungae.domain.user.dto.request.UserLogInRequestDto;
import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.NotFoundUserException;
import com.example.hobbybungae.domain.user.exception.NotMachingPasswordException;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.global.exception.ErrorCode;
import com.example.hobbybungae.security.JwtUtil;
import com.example.hobbybungae.security.UserDetailsServiceImpl;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({UserSignUpService.class, UserValidator.class,
	UserLogInService.class, User.class,
	BCryptPasswordEncoder.class, WebSecurityConfig.class,
	JwtUtil.class, UserDetailsServiceImpl.class, AuthenticationConfiguration.class})
class UserLogInServiceTest {

	private final UserSignUpRequestDto preData = UserSignUpRequestDto.builder()
		.idName("jihoon9898")
		.name("jihoon")
		.nickName("sameNickName")
		.email("jihoonmail@mail.com")
		.password("jihoon1234")
		.passwordReconfirm("jihoon1234")
		.introduction("introduce me : Yo! I'm tony lim! Yo!")
		.build();

	@Autowired
	private UserLogInService userLogInService;

	@Autowired
	private UserSignUpService userSignUpService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		userSignUpService.signUp(preData);
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("해피케이스 스텁에 대해 로그인 시도 시,성공결과를 반환합니다.")
	public void 로그인_해피케이스() {
		// GIVEN
		UserLogInRequestDto requestDto = UserLogInRequestDto.builder()
			.idName(preData.idName())
			.password(preData.password())
			.build();

		// WHEN
		ResponseEntity<UserResponseDto> response = userLogInService.login(requestDto);

		// THEN
		String token = jwtUtil.createToken(requestDto.idName());
		assertEquals(Objects.requireNonNull(response.getBody()).idName(), requestDto.idName());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertTrue(Objects.requireNonNull(response.getHeaders().get(HttpHeaders.AUTHORIZATION)).contains(token));
	}

	@Test
	@DisplayName("해피케이스에 대해 비밀번호 검증 성공 처리합니다.")
	public void 비밀번호_검증_해피케이스() {
		// GIVEN
		UserLogInRequestDto requestDto = UserLogInRequestDto.builder()
			.idName(preData.idName())
			.password(preData.password())
			.build();

		// WHEN
		User user = userRepository.findByIdName(preData.idName()).orElseThrow(NotFoundUserException::new);

		// THEN
		userLogInService.verifyMatchingPassword(requestDto, user);
	}

	@Test
	@DisplayName("언해피케이스에 대해 비밀번호 검증 예외 처리합니다.")
	public void 비밀번호_검증_언해피케이스_예외처리() {
		// GIVEN
		UserLogInRequestDto requestDto = UserLogInRequestDto.builder()
			.idName(preData.idName())
			.password("RandomPassword!@%)")
			.build();

		// WHEN
		User user = userRepository.findByIdName(preData.idName()).orElseThrow(NotFoundUserException::new);
		NotMachingPasswordException exception = assertThrows(NotMachingPasswordException.class, () ->
			userLogInService.verifyMatchingPassword(requestDto, user)
		);

		// THEN
		assertEquals(exception.getErrorCode().getCode(), HttpStatus.BAD_REQUEST);
		assertEquals(exception.getErrorCode().getMessage(), ErrorCode.NOT_MATCHING_PASSWORD.getMessage());
	}
}