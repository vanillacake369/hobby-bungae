package com.example.hobbybungae.domain.hobby.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({HobbyService.class, Hobby.class})
class HobbyServiceTest {

	@Autowired
	private HobbyService hobbyService;

	@Autowired
	private HobbyRepository hobbyRepository;

	@Test
	@DisplayName("취미를 검증합니다.")
	public void 취미존재여부검증() {
		// GIVEN
		String hobbyName = "sdlkajlskdgjlsdakjglajk";
		Hobby hobby = Hobby.builder()
			.hobbyName(hobbyName)
			.build();
		// THEN
		assertThrows(NotFoundHobbyException.class, () -> {
			hobbyService.validateHobbyExistence(hobby);
		});
	}
}