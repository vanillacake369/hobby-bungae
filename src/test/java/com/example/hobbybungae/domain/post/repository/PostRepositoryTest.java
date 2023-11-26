package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
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
	UserRepository userRepository;

	private User savedUser = null;

	@BeforeEach
	void setUp() {
		User user = User.builder()
			.name("user")
			.idName("userIdName")
			.password("userPassword")
			.introduction("userIntroduction")
			.build();
		savedUser = userRepository.save(user);
	}

	@Test
	@DisplayName("로그인된 User를 입력받아 Post를 생성 및 업데이트 합니다.ㅏ")
	public void 로그인된_User의_Post_생성() {
		assert savedUser != null;
		// GIVEN
		Post post = Post.builder()
			.title("postTitle")
			.contents("postContents")
			.build();

		// WHEN
		post.setAuthor(savedUser);
		postRepository.save(post);

		// THEN
		Post foundPost = postRepository.findByUser(savedUser)
			.orElseThrow(() -> new NotFoundPostException("post.user", savedUser.toString()));
		assertEquals(post, foundPost);
	}
}