package com.example.hobbybungae.domain.user.entity;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.userProfile.dto.UserProfileUpdateRequestDto;
import com.example.hobbybungae.domain.userProfile.entity.UserHobby;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends TimeStamp {

	@OneToMany(targetEntity = UserHobby.class, mappedBy = "user")
	private final List<UserHobby> userHobbies = new ArrayList<>();

	@OneToMany(targetEntity = Post.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Post> posts = new ArrayList<>();

	@OneToMany(targetEntity = Comment.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String idName;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String nickName;
	@Column(nullable = false)
	private String password;
	@Column
	private String introduction;

	/**
	 * User와 다대다 연관관계의 Hobby를 입력받아 연관관계 해결
	 * @param hobby 입력된 Hobby
	 */
	public void addHobby(Hobby hobby) {
		UserHobby userHobby = UserHobby.addUserAndHobby(this, hobby);
		userHobbies.add(userHobby);
	}

	/**
	 * User와 기존에 연관되어있는 UserHobby들을 탐색한 뒤, Hobby의 연관관계 제거
	 */
	public void update(UserProfileUpdateRequestDto requestDto, String password) {
		this.idName = requestDto.idName();
		this.name = requestDto.name();
		this.introduction = requestDto.introduction();
		this.password = password;
		// 기존 연관된 취미와의 연관관계 끊기
		removeHobbies();
		// 각 Hobby들에 대한 연관관계 저장
		List<Hobby> hobbies = requestDto.hobbies();
		hobbies.forEach(this::addHobby);
	}

	public void removeHobbies() {
		if (!userHobbies.isEmpty()) {
			userHobbies.forEach(userHobby ->
				userHobby.getHobby()
					.getUserHobbies()
					.remove(userHobby)
			);
		}
		userHobbies.clear();
	}
}
