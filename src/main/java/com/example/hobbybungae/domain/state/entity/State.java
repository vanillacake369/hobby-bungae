package com.example.hobbybungae.domain.state.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "do")
	private String stateDo;

	@Column(nullable = false, name = "si")
	private String stateSi;

	@Column(nullable = false, name = "gu")
	private String stateGu;

	@Override
	public String toString() {
		return "State{" +
			"stateDo='" + stateDo + '\'' +
			", stateSi='" + stateSi + '\'' +
			", stateGu='" + stateGu + '\'' +
			'}';
	}
}
