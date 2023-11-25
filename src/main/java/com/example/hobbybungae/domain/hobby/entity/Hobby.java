package com.example.hobbybungae.domain.hobby.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "hobbies")
@NoArgsConstructor
public class Hobby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String hobbyName;

//    @OneToMany(mappedBy = "hobby")
//    private List<UserHobby> userHobbyList = new ArrayList<>();

	@Builder
	public Hobby(String hobbyName) {
		this.hobbyName = hobbyName;
	}
}
