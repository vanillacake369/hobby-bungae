package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostHobbyRepository postHobbyRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	@Autowired
	UserRepository userRepository;

	private Post post = null;

	@BeforeEach
	void setUp() {
		Post post = Post.builder()
			.title("postTitle")
			.contents("postContents")
			.build();
		this.post = postRepository.save(post);
	}

	@Test
	@DisplayName("로그인된 User를 입력받아 Post를 생성 및 업데이트 합니다.ㅏ")
	public void 로그인된_User의_Post_생성() {
		// GIVEN
		User user = User.builder()
			.name("user")
			.idName("userIdName")
			.password("userPassword")
			.introduction("userIntroduction")
			.build();
		User savedUser = userRepository.save(user);

		// WHEN
		post.setAuthor(savedUser);

		// THEN
		Post foundPost = postRepository.findByUser(savedUser)
			.orElseThrow(() -> new NotFoundPostException("post.user", savedUser.toString()));
		assertEquals(post, foundPost);
	}

	@Test
	@DisplayName("Hobby를 입력받아 PostHobby를 생성, 이에 대한 다대다 양방향 관계를 resolve합니다.")
	public void post_hobby_양방향연관관계_해결() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);

		// WHEN 서비스단
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);

		// THEN
		List<PostHobby> postHobbyByPost = postHobbyRepository.findPostHobbyByPost(post);
		List<PostHobby> postHobbyByBaseball = postHobbyRepository.findPostHobbyByHobby(hobbyBaseball);
		List<PostHobby> postHobbyBySoccer = postHobbyRepository.findPostHobbyByHobby(hobbySoccer);
		assertTrue(postHobbyByPost.containsAll(postHobbyByBaseball));
		assertTrue(postHobbyByPost.containsAll(postHobbyBySoccer));
	}

	@Test
	@DisplayName("게시글의 취미 수정 시, 게시글에 대한 기존 취미의 연관관계를 끊고, 새로운 연관관계를 생성합니다.")
	public void 기존취미_제거_새로운연관관계생성() {
		assert post != null;
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);

		// WHEN
		post.removeHobbies(); // 서비스 단에서는 update(requestDto)로 실행됨

		// THEN
		List<PostHobby> postHobbyByPost = postHobbyRepository.findPostHobbyByPost(post);
		boolean hasBaseBall = postHobbyByPost
			.stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(hobbyBaseball));
		boolean hasSoccer = postHobbyByPost
			.stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(hobbyBaseball));
		boolean hasSoccerRelatesThisPost = hobbySoccer.getPostHobbyList()
			.stream()
			.anyMatch(postHobby -> postHobby.getPost().equals(post));
		boolean hasBaseballRelatesThisPost = hobbyBaseball.getPostHobbyList()
			.stream()
			.anyMatch(postHobby -> postHobby.getPost().equals(post));
		assertFalse(hasBaseBall);
		assertFalse(hasSoccer);
		assertFalse(hasSoccerRelatesThisPost);
		assertFalse(hasBaseballRelatesThisPost);
	}
}