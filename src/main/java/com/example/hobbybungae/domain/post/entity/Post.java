package com.example.hobbybungae.domain.post.entity;


import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends TimeStamp {

	@OneToMany(targetEntity = PostHobby.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<PostHobby> postHobbyList = new ArrayList<>();

	@OneToMany(targetEntity = Comment.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Comment> comments = new ArrayList<>();

	@ManyToOne
	@Setter(AccessLevel.NONE)
	@JoinColumn(name = "state_id")
	State state;

	@ManyToOne
	@Setter(AccessLevel.NONE)
	@JoinColumn(name = "user_id")
	User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String title;

	@Column(nullable = false, length = 500)
	private String contents;

	@Builder
	public Post(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public Post(PostRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.contents = requestDto.getContent();
		this.state = requestDto.getState();
		// 각 Hobby들에 대한 연관관계 저장
		List<Hobby> hobbies = requestDto.getHobbies();
		hobbies.forEach(this::addHobby);
	}

	public void update(PostRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.contents = requestDto.getContent();
		this.state = requestDto.getState();
		// 각 Hobby들에 대한 연관관계 저장
		List<Hobby> hobbies = requestDto.getHobbies();
		hobbies.forEach(this::addHobby);
	}

	/**
	 * Hobby를 입력받아 다대다 연관관계 해결
	 *
	 * @param hobby 입력된 Hobby
	 */
	public void addHobby(Hobby hobby) {
		PostHobby postHobby = new PostHobby(this, hobby);
		postHobby.addPostAndHobby(this, hobby);
		postHobbyList.add(postHobby);
	}

	public void setAuthor(User user) {
		// 기존 연관된 User 제거
		if (this.user != null) {
			this.user.getPosts().remove(this);
		}
		// User 추가 후, User에서의 posts에 본인 추가
		this.user = user;
		user.getPosts().add(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Post post)) {
			return false;
		}
		return getId().equals(post.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "Post{" +
			"postHobbyList=" + postHobbyList +
			", comments=" + comments +
			", state=" + state +
			", user=" + user +
			", id=" + id +
			", title='" + title + '\'' +
			", contents='" + contents + '\'' +
			'}';
	}
}
