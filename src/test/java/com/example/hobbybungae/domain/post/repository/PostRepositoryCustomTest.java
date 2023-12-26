package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hobbybungae.config.QueryDslConfiguration;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.unit.PostSelectCondition;
import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.state.exception.NotFoundStateException;
import com.example.hobbybungae.domain.state.repository.StateRepository;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(QueryDslConfiguration.class)
class PostRepositoryCustomTest {

	@Autowired
	StateRepository stateRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostRepositoryCustomImpl postRepositoryCustom;
	private User user;
	private State state;

	@BeforeEach
	void setUp() {
		User user = User.builder()
			.id(1L)
			.idName("honni98")
			.name("jihoon")
			.email("jihoon98@gmail.com")
			.nickName("tony98")
			.password("whatSup12***")
			.build();
		this.state = stateRepository
			.findByStateDoAndStateSiAndStateGu(
				"전라북도",
				"전주시",
				"완산구"
			)
			.orElseThrow(NotFoundStateException::new);
		this.user = userRepository.save(user);
	}

	@Test
	@DisplayName("Condition에 따른 Post 리스트를 조회합니다.")
	public void querydsl_Post조회_ByCondition() {
		// GIVEN
		Post post = Post.builder()
			.title("postTitle")
			.contents("postContents")
			.state(state)
			.user(user)
			.build();
		postRepository.save(post);
		PostSelectCondition condition = PostSelectCondition.builder()
			.state(state)
			.userId(user.getId())
			.title(post.getTitle())
			.contents(post.getContents())
			.build();

		// WHEN
		List<Post> posts = postRepositoryCustom.findPostsBy(condition);

		// THEN
		assertEquals(1, posts.size());
		assertEquals(post, posts.get(0));
	}

	@Test
	@DisplayName("Condition과 Paging에 따른 Post에 대한 Page 리스트를 조회합니다.")
	public void querydsl_Post_페이징_조회_ByCondition() {
		// GIVEN
		for (int i = 1; i < 21; i++) {
			int rand = new Random().nextInt(i * i);
			String title = "postTitle" + rand;
			String content = "postContents" + rand;
			Post post = Post.builder()
				.title(title)
				.contents(content)
				.state(state)
				.user(user)
				.build();
			postRepository.save(post);
		}
		Pageable pageable = PageRequest.of(
			0,
			10
		);
		PostSelectCondition condition = PostSelectCondition.builder()
			.state(state)
			.userId(user.getId())
			.build();

		// WHEN
		Page<Post> posts = postRepository.findPostsPaging(condition, pageable);

		// THEN
		assertEquals(10, posts.getSize());
		for (Post p : posts) {
			System.out.println("p.toString() = " + p.toString());
		}
	}
}

