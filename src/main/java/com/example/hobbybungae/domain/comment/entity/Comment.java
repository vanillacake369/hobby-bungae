package com.example.hobbybungae.domain.comment.entity;

import com.example.hobbybungae.domain.comment.dto.CommentRequestDto;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Setter(AccessLevel.NONE)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	@Setter(AccessLevel.NONE)
	private Post post;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String text;

	@Builder
	public Comment(String text) {
		this.text = text;
	}

	public Comment(CommentRequestDto requestDto, User user, Post post) {
		this.text = requestDto.getText();
		this.user = user;
		setPost(post);
	}

	public void update(CommentRequestDto requestDto, Post post) {
		setPost(post);
		this.text = requestDto.getText();
	}

	public void setPost(Post post) {
		// 기존 연관된 Comment 제거
		if (this.post != null) {
			this.post.getComments().remove(this);
		}
		// Post 추가 후, Post에서의 comments에 본인 추가
		this.post = post;
		post.getComments().add(this);
	}

	public void setAuthor(User user) {
		// 기존 연관된 Comment 제거
		if (this.user != null) {
			this.user.getComments().remove(this);
		}
		// User 추가 후, User의 comments에 본인 추가
		this.user = user;
		user.getComments().add(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Comment comment)) {
			return false;
		}
		return getId().equals(comment.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
