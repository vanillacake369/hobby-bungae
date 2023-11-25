package com.example.hobbybungae.domain.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hobbybungae.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("User 엔티티에 대한 테스트")

	public void 유저테이블_테스트() {
		// GIVEN
		User newUser = User.builder()
			.idName("jihoon9898")
			.name("jihoon")
			.password("password1234")
			.build();
		// WHEN
		userRepository.save(newUser);
		User selectedUser = userRepository.findByIdName("jihoon9898").get();
		// THEN
		assertEquals(newUser, selectedUser);
	}
}