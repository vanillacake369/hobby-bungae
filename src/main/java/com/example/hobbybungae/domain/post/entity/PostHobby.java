package com.example.hobbybungae.domain.post.entity;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "post_hobby")
@NoArgsConstructor
public class PostHobby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@Setter // 연관관계 값 입력을 위해 setter를 열음
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(cascade = CascadeType.ALL)
	@Setter(AccessLevel.NONE) // setter를 잠가버림
	@JoinColumn(name = "hobby_id", nullable = false)
	private Hobby hobby;

	@Builder
	public PostHobby(Post post, Hobby hobby) {
		this.post = post;
		this.hobby = hobby;
	}
}
