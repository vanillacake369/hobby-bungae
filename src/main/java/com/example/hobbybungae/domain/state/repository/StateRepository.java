package com.example.hobbybungae.domain.state.repository;

import com.example.hobbybungae.domain.state.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
