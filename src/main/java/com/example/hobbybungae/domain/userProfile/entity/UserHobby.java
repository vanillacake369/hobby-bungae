package com.example.hobbybungae.domain.userProfile.entity;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_hobby")
@NoArgsConstructor
public class UserHobby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "hobby_id", nullable = false)
	private Hobby hobby;

	@Builder
	public UserHobby(User user, Hobby hobby) {
		this.user = user;
		this.hobby = hobby;
	}

	// Hobby 추가 및 업데이트
	public static UserHobby addUserAndHobby(User user, Hobby hobby) {
		UserHobby userHobby = new UserHobby(user, hobby);
		userHobby.getHobby().getUserHobbies().add(userHobby);
		return userHobby;
	}
}