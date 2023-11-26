package com.example.hobbybungae.domain.state.service;

import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.state.exception.NotFoundStateException;
import com.example.hobbybungae.domain.state.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateService {

	private final StateRepository stateRepository;


	public void validateStateExistence(State state) {
		log.info("지역 검증 시작");
		stateRepository.findByStateDoAndStateSiAndStateGu(state.getStateDo(), state.getStateSi(), state.getStateGu())
			.orElseThrow(() -> {
				throw new NotFoundStateException("state", state.toString());
			});
		log.info("지역 검증 통과");
	}
}
