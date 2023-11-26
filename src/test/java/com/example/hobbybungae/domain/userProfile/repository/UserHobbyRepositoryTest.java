package com.example.hobbybungae.domain.userProfile.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.exception.NotFoundUserException;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
}