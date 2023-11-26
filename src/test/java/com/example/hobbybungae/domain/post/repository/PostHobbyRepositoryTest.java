package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import java.util.List;
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
class PostHobbyRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostHobbyRepository postHobbyRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	@Test
	@DisplayName("Hobby를 입력받아 PostHobby를 생성, 이에 대한 다대다 양방향 관계를 resolve합니다.")
	public void post_hobby_양방향연관관계_해결() {
		// GIVEN
		Post post = Post.builder().title("야구 번개 모아요").contents("같이 야구 하실 분 9명 모아요~").build();
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		// WHEN 서비스단
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);
		postRepository.save(post);
		// THEN
		List<PostHobby> postHobbyByPost = postHobbyRepository.findPostHobbyByPost(post);
		List<PostHobby> postHobbyByBaseball = postHobbyRepository.findPostHobbyByHobby(hobbyBaseball);
		List<PostHobby> postHobbyBySoccer = postHobbyRepository.findPostHobbyByHobby(hobbySoccer);
		assertTrue(postHobbyByPost.containsAll(postHobbyByBaseball));
		assertTrue(postHobbyByPost.containsAll(postHobbyBySoccer));
	}
}