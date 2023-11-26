package com.example.hobbybungae.domain.userProfile.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.NotFoundUserException;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class UserHobbyRepositoryTest {

	@Autowired
	UserHobbyRepository userHobbyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	public static Stream<Arguments> getUserIdAndNewHobbyList() {
		return Stream.of(
			Arguments.of(
				1L,
				List.of(
					new Hobby("야구"),
					new Hobby("축구"),
					new Hobby("농구")
				)
			)
		);
	}

	@Test
	@DisplayName("Hobby를 입력받아 UserHobby를 생성, 이에 대한 다대다 양방향 관계를 resolve합니다.")
	public void user_hobby_다대다관계_연관관계_해결() {
		// GIVEN
		User user = User.builder()
			.name("user")
			.idName("userIdName")
			.password("userPassword")
			.introduction("userIntroduction")
			.build();
		Hobby hobby = Hobby.builder().hobbyName("야구").build();
		hobbyRepository.save(hobby);
		// WHEN
		user.addHobby(hobby);
		userRepository.save(user);
		// THEN
		UserHobby userHobbyOfUser = userHobbyRepository.findUserHobbyByUser(user)
			.orElseThrow(() -> new NotFoundUserException("user", user.toString()));
		UserHobby userHobbyOfHobby = userHobbyRepository.findUserHobbyByHobby(hobby)
			.orElseThrow(() -> new NotFoundHobbyException("hobby", hobby.toString()));
		assertEquals(userHobbyOfUser, userHobbyOfHobby);
	}

	@ParameterizedTest
	@MethodSource("getUserIdAndNewHobbyList")
	@DisplayName("Hobby를 입력받아 수정 시, 현재 유저의 취미를 모두 삭제하고 새로 등록합니다.")
	public void 기존취미삭제_새로운취미등록(Long id, List<Hobby> hobbies) {
		// GIVEN
		User user = User.builder()
			.id(id)
			.idName("idName")
			.name("name")
			.password("password")
			.build();
		Hobby golf = new Hobby("골프");
		Hobby tennis = new Hobby("테니스");
		user.addHobby(golf);
		user.addHobby(tennis);

		// WHEN
		List<UserHobby> userHobbyList = userHobbyRepository.findAllByUserId(id);
		userHobbyRepository.deleteAll(userHobbyList);
		for (Hobby hobby : hobbies) {
			user.addHobby(hobby);
		}
		userRepository.save(user);

		// THEN
		boolean leftoutUserOfGolf = golf.getUserHobbyList().stream()
			.anyMatch(userHobby -> userHobby.getUser().equals(user));
		boolean leftoutUserOfTennis = tennis.getUserHobbyList().stream()
			.anyMatch(userHobby -> userHobby.getUser().equals(user));
		boolean hasGolf = user.getUserHobbies().stream()
			.anyMatch(userHobby -> userHobby.getHobby().equals(golf));
		boolean hasTennis = user.getUserHobbies().stream()
			.anyMatch(userHobby -> userHobby.getHobby().equals(tennis));
		assertFalse(leftoutUserOfGolf);
		assertFalse(leftoutUserOfTennis);
		assertFalse(hasGolf);
		assertFalse(hasTennis);
	}
}