package com.example.hobbybungae.domain.state.repository;

import com.example.hobbybungae.domain.state.entity.State;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {

	Optional<State> findByStateDoAndStateSiAndStateGu(String stateDo, String stateSi, String stateGu);
}
