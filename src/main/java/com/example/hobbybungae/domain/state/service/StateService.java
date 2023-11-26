package com.example.hobbybungae.domain.state.service;

import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.state.exception.NotFoundStateException;
import com.example.hobbybungae.domain.state.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateService {

	private final StateRepository stateRepository;


	public void validateStateExistence(State state) {
		stateRepository.findByStateDoAndStateSiAndStateGu(state.getStateDo(), state.getStateSi(), state.getStateGu())
			.orElseThrow(() -> {
				throw new NotFoundStateException("state", state.toString());
			});
	}
}
