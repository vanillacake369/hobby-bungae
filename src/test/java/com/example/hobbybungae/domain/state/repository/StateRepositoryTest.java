package com.example.hobbybungae.domain.state.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.state.entity.State;
import java.util.Optional;
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
class StateRepositoryTest {

	@Autowired
	StateRepository stateRepository;

	@Test
	@DisplayName("저장한 시들을 확인합니다.")
	public void 도시데이터확인() {
		// WHEN
		Optional<State> foundState = stateRepository.findByStateDoAndStateSiAndStateGu("경기도", "수원시",
			"장안구");

		// THEN
		assertTrue(foundState.isPresent());
	}
}