package com.example.hobbybungae.domain.post.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({PostService.class, Post.class})
class PostServiceTest {

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@Test
	@DisplayName("취미를 검증합니다.")
	public void 취미존재여부검증() throws Exception {
		// GIVEN
		String hobbyName = "sdlkajlskdgjlsdakjglajk";
		Hobby hobby = Hobby.builder()
			.hobbyName(hobbyName)
			.build();
		// THEN
		assertThrows(NotFoundHobbyException.class, () -> {
			postService.validateHobbyExistence(hobby);
		});
	}
}