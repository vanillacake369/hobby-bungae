package com.example.hobbybungae.domain.post.entity;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
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
@Table(name = "post_hobby")
@NoArgsConstructor
public class PostHobby {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
//	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "hobby_id", nullable = false)
	private Hobby hobby;

	@Builder
	public PostHobby(Post post, Hobby hobby) {
		this.post = post;
		this.hobby = hobby;
	}

	/**
	 * Hobby 입력에 대한 Post 생성 연관관계 편의 메서드
	 * @param post  생성한 Post
	 * @param hobby Post 생성을 위한 입력된 Hobby
	 */
	public static PostHobby addPostAndHobby(Post post, Hobby hobby) {
		PostHobby postHobby = new PostHobby(post, hobby);
		postHobby.hobby.getPostHobbyList().add(postHobby);
		return postHobby;
	}
}
