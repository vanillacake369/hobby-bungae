package com.example.hobbybungae.domain.post.entity;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import jakarta.persistence.Entity;
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
@Table(name = "post_hobby")
@NoArgsConstructor
public class PostHobby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne
	@JoinColumn(name = "hobby_id", nullable = false)
	private Hobby hobby;

	@Builder
	public PostHobby(Post post, Hobby hobby) {
		this.post = post;
		this.hobby = hobby;
	}

	/**
	 * Hobby입력에 대한 Post 생성 연관관계 편의 메서드
	 *
	 * @param post
	 * @param hobby
	 */
	public void addPostAndHobby(Post post, Hobby hobby) {
		this.post = post;
		this.hobby = hobby;
	}
}
