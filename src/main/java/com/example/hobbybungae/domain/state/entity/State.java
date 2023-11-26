package com.example.hobbybungae.domain.state.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
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

	@Column(name = "si")
	private String stateSi;

	@Column(name = "gu")
	private String stateGu;

	@Builder
	public State(String stateDo, String stateSi, String stateGu) {
		this.stateDo = stateDo;
		this.stateSi = stateSi;
		this.stateGu = stateGu;
	}

	@Override
	public String toString() {
		return "State{" +
			"stateDo='" + stateDo + '\'' +
			", stateSi='" + stateSi + '\'' +
			", stateGu='" + stateGu + '\'' +
			'}';
	}
}
