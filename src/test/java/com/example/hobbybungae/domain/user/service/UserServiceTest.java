package com.example.hobbybungae.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.request.VerifyNicknameRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.dto.response.VerifyNicknameResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.DuplicatedIdNameException;
import com.example.hobbybungae.domain.user.exception.DuplicatedNickNameException;
import com.example.hobbybungae.domain.user.exception.NotMachingPasswordReconfirm;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import com.example.hobbybungae.global_exception.ErrorCode;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({UserService.class, BCryptPasswordEncoder.class, User.class, UserHobby.class, Post.class,
	Comment.class})
class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	public static Stream<Arguments> duplicatedUserSignUpRequest() {
		return Stream.of(
			Arguments.of(
				UserSignUpRequestDto.builder()
					.idName("jihoon9898")
					.name("jihoon")
					.nickName("sameNickName")
					.password("1234")
					.build()
			)
		);
	}

	public static Stream<Arguments> invalidPasswordSignUpRequest() {
		return Stream.of(
			Arguments.of(
				UserSignUpRequestDto.builder()
					.idName("userIdName23")
					.name("userName")
					.nickName("notSameNickName")
					.password("qweQWnotSameNickName!@$")
					.build()
			)
		);
	}

	public static Stream<Arguments> signUpRequestOfHappyCase() {
		return Stream.of(
			Arguments.of(
				UserSignUpRequestDto.builder()
					.idName("sparta1234")
					.name("happyhappy")
					.password("1234qwer")
					.build()
			)
		);
	}

	@BeforeEach
	@Transactional
	void setUp() {
		User user = User.builder()
			.idName("jihoon9898")
			.name("jihoon")
			.nickName("sameNickName")
			.password("1234")
			.build();
		userRepository.save(user);
	}

	@AfterEach
	@Transactional
	void tearDown() {
		userRepository.deleteAll();
	}

	@ParameterizedTest
	@DisplayName("중복회원을 검증합니다.")
	@MethodSource("duplicatedUserSignUpRequest")
	public void 중복회원_검증(UserSignUpRequestDto requestDto) {
		// WHEN
		boolean hasDuplicatedIdName = userService.hasDuplicatedIdName(requestDto.idName());
		boolean hasDuplicatedNickName = userService.hasDuplicatedNickName(requestDto.nickName());
		boolean hasNicknameInPassword = userService.hasNicknameInPassword(requestDto.password(), requestDto.nickName());

		// THEN
		assertTrue(hasDuplicatedIdName);
		assertTrue(hasDuplicatedNickName);
		assertTrue(hasNicknameInPassword);
	}

	@Test
	@DisplayName("중복 닉네임 입력 오류에 대해 검증합니다.")
	public void 중복닉네임_검증() {
		// GIVEN
		UserSignUpRequestDto sameNickNameRequest = UserSignUpRequestDto.builder()
			.nickName("sameNickName")
			.build();

		// WHEN
		DuplicatedNickNameException exception = assertThrows(
			DuplicatedNickNameException.class, () ->
				userService.signUp(sameNickNameRequest)
		);

		// THEN
		assertEquals(exception.getErrorCode().getCode(), HttpStatus.BAD_REQUEST);
		assertEquals(exception.getErrorCode().getMessage(), ErrorCode.DUPLICATED_NICK_NAME.getMessage());
	}

	@Test
	@DisplayName("확인 버튼을 눌러 중복 닉네임 입력 오류에 대해 검증합니다.")
	public void 확인버튼_중복닉네임_검증() {
		// GIVEN
		VerifyNicknameRequestDto notDuplicatedNickNameRequest = VerifyNicknameRequestDto.builder()
			.nickName("can00tDupliCated")
			.build();
		VerifyNicknameRequestDto duplicatedNickNameRequest = VerifyNicknameRequestDto.builder()
			.nickName("sameNickName")
			.build();

		// WHEN
		ResponseEntity<VerifyNicknameResponseDto> responseOfHappyCase = userService.verifyNicknameDuplication(
			notDuplicatedNickNameRequest);
		DuplicatedNickNameException exception = assertThrows(
			DuplicatedNickNameException.class, () ->
				userService.verifyNicknameDuplication(duplicatedNickNameRequest)
		);

		// THEN
		assertEquals(responseOfHappyCase.getStatusCode(), HttpStatus.OK);
		assertTrue(Objects.requireNonNull(responseOfHappyCase.getBody()).isNotDuplicatedNickname());
		assertEquals(exception.getErrorCode().getCode(), HttpStatus.BAD_REQUEST);
		assertEquals(exception.getErrorCode().getMessage(), ErrorCode.DUPLICATED_NICK_NAME.getMessage());
	}

	@ParameterizedTest
	@DisplayName("닉네임이 비밀번호 안에 있는지에 대해 형식검증합니다.")
	@MethodSource("invalidPasswordSignUpRequest")
	public void 비밀번호_형식검증(UserSignUpRequestDto requestDto) {
		// WHEN
		boolean hasNicknameInPassword = userService.hasNicknameInPassword(requestDto.password(), requestDto.nickName());

		// THEN
		assertTrue(hasNicknameInPassword);
	}

	@Test
	@DisplayName("비밀번호 입력값과 비밀번호 재확인 입력값이 서로 동일한지 검증합니다.")
	public void 비밀번호_재확인검증() {
		// GIVEN
		UserSignUpRequestDto requestDtoHappyCase = UserSignUpRequestDto.builder()
			.password("password123")
			.passwordReconfirm("password123")
			.build();
		UserSignUpRequestDto requestDtoErrorCase = UserSignUpRequestDto.builder()
			.password("password123")
			.passwordReconfirm("notSamePassword123")
			.build();

		// WHEN
		userService.validatePasswordReconfirmation(requestDtoHappyCase.password(),
			requestDtoHappyCase.passwordReconfirm());

		NotMachingPasswordReconfirm exception = assertThrows(NotMachingPasswordReconfirm.class, () ->
			userService.validatePasswordReconfirmation(requestDtoErrorCase.password(),
				requestDtoErrorCase.passwordReconfirm())
		);

		// THEN
		assertEquals(exception.getErrorCode().getCode(), HttpStatus.BAD_REQUEST);
	}

	@ParameterizedTest
	@DisplayName("중복된 회원은 회원가입에 실패합니다.")
	@MethodSource("duplicatedUserSignUpRequest")
	public void 회원가입_언해피케이스_중복회원(UserSignUpRequestDto requestDto) {
		// WHEN
		DuplicatedIdNameException duplicatedIdNameException = assertThrows(DuplicatedIdNameException.class, () ->
			userService.signUp(requestDto)
		);
		// THEN
		assertEquals(duplicatedIdNameException.getErrorCode(), ErrorCode.DUPLICATED_ID_NAME);
	}

	@ParameterizedTest
	@DisplayName("새로운 회원이 회원가입에 성공합니다.")
	@MethodSource("signUpRequestOfHappyCase")
	public void 회원가입_해피케이스(UserSignUpRequestDto requestDto) {
		// WHEN
		ResponseEntity<UserResponseDto> response = userService.signUp(requestDto);

		// THEN
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}