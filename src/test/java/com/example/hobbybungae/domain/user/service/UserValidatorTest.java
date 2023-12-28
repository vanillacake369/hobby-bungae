package com.example.hobbybungae.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.NotMachingPasswordReconfirm;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({UserValidator.class, User.class, UserHobby.class, Post.class, Comment.class})
class UserValidatorTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserValidator userValidator;

	public static Stream<Arguments> duplicatedUserSignUpRequest() {
		return Stream.of(
			Arguments.of(
				UserSignUpRequestDto.builder()
					.idName("jihoon9898")
					.name("jihoon")
					.nickName("sameNickName")
					.password("1234sameNickName1234")
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
					.email("randmail@mail.com")
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
			.email("jihoonmail@mail.com")
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
		boolean hasDuplicatedIdName = userValidator.hasDuplicatedIdName(requestDto.idName());
		boolean hasDuplicatedNickName = userValidator.hasDuplicatedNickName(requestDto.nickName());
		boolean hasNicknameInPassword = userValidator.hasNicknameInPassword(requestDto.password(), requestDto.nickName());

		// THEN
		assertTrue(hasDuplicatedIdName);
		assertTrue(hasDuplicatedNickName);
		assertTrue(hasNicknameInPassword);
	}


	@ParameterizedTest
	@DisplayName("닉네임이 비밀번호 안에 있는지에 대해 형식검증합니다.")
	@MethodSource("invalidPasswordSignUpRequest")
	public void 비밀번호_형식검증(UserSignUpRequestDto requestDto) {
		// WHEN
		boolean hasNicknameInPassword = userValidator.hasNicknameInPassword(requestDto.password(), requestDto.nickName());

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
			.email("mail@mail.com")
			.build();
		UserSignUpRequestDto requestDtoErrorCase = UserSignUpRequestDto.builder()
			.password("password123")
			.passwordReconfirm("notSamePassword123")
			.email("mail@mail.com")
			.build();

		// WHEN
		userValidator.validatePasswordReconfirmation(requestDtoHappyCase.password(),
			requestDtoHappyCase.passwordReconfirm());

		NotMachingPasswordReconfirm exception = assertThrows(NotMachingPasswordReconfirm.class, () ->
			userValidator.validatePasswordReconfirmation(requestDtoErrorCase.password(),
				requestDtoErrorCase.passwordReconfirm())
		);

		// THEN
		assertEquals(exception.getErrorCode().getCode(), HttpStatus.BAD_REQUEST);
	}

}