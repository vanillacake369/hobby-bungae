package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PostHobbyRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostHobbyRepository postHobbyRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	@Test
	@DisplayName("Hobby를 입력받아 PostHobby를 생성, 이에 대한 양방향 관계를 resolve합니다.")
	public void Hobby간_양방향연관관계_해결() {
		Post post = Post.builder().title("야구 번개 모아요").contents("같이 야구 하실 분 9명 모아요~").build();
		Hobby hobby = Hobby.builder().hobbyName("야구").build();
		// WHEN
		post.addHobby(hobby);
		postRepository.save(post);
//		postHobbyRepository.save(postHobby);
//		hobbyRepository.save(hobby);
		// THEN
		PostHobby postHobbyOfPost = postHobbyRepository.findPostHobbyByPost(post).get();
		PostHobby postHobbyOfHobby = postHobbyRepository.findPostHobbyByHobby(hobby).get();
		assertEquals(postHobbyOfHobby, postHobbyOfPost);
	}
}