package com.example.hobbybungae.domain.hobby.entity;

import com.example.hobbybungae.domain.post.entity.PostHobby;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "hobby")
@NoArgsConstructor
public class Hobby {

	@OneToMany(targetEntity = PostHobby.class, mappedBy = "hobby")
	private final List<PostHobby> postHobbyList = new ArrayList<>();

	@OneToMany(targetEntity = UserHobby.class, mappedBy = "hobby")
	private final List<UserHobby> userHobbyList = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String hobbyName;

	@Builder
	public Hobby(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	@Override
	public String toString() {
		return "Hobby{" +
			"postHobbyList=" + postHobbyList +
			", userHobbyList=" + userHobbyList +
			", id=" + id +
			", hobbyName='" + hobbyName + '\'' +
			'}';
	}
}
