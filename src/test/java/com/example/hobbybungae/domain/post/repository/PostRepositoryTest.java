package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
	private User user = null;

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
		Post post = new Post(1L, "postTitle", "postContents", user);
		this.user = userRepository.save(user);
		/**
		 * Hibernate: select u1_0.id,u1_0.created_at,u1_0.email,u1_0.id_name,u1_0.introduction,u1_0.modified_at,u1_0.name,u1_0.nick_name,u1_0.password,c1_0.user_id,c1_0.id,c1_0.post_id,c1_0.text from user u1_0 left join comment c1_0 on u1_0.id=c1_0.user_id where u1_0.id=?
		 * Hibernate: insert into user (created_at,email,id_name,introduction,modified_at,name,nick_name,password) values (?,?,?,?,?,?,?,?)
		 */
		this.post = postRepository.save(post);
		/**
		 * Hibernate: select p1_0.id,p1_0.contents,p1_0.created_at,p1_0.modified_at,p1_0.state_id,p1_0.title,p1_0.user_id,c1_0.post_id,c1_0.id,c1_0.text,c1_0.user_id from post p1_0 left join comment c1_0 on p1_0.id=c1_0.post_id where p1_0.id=?
		 * Hibernate: insert into post (contents,created_at,modified_at,state_id,title,user_id) values (?,?,?,?,?,?)
		 */
	}

	@AfterEach
	void tearDown() {
		postHobbyRepository.deleteAll();
		postRepository.deleteAll();
		hobbyRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("PostHobby 중간조인테이블에서 데이터 삭제 이후 조회 시, left over entity가 존재합니다.")
	public void 중간조인테이블_데이터삭제_leftover_entity존재() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);
		/**
		 * Hibernate: insert into hobby (hobby_name) values (?)
		 * Hibernate: insert into hobby (hobby_name) values (?)
		 * Hibernate: insert into post_hobby (hobby_id,post_id) values (?,?)
		 * Hibernate: insert into post_hobby (hobby_id,post_id) values (?,?)
		 */

		// WHEN
		postHobbyRepository.deleteAll(); // JPQL
		/**
		 * Hibernate: delete from post_hobby
		 * Hibernate: select h1_0.id,h1_0.hobby_name from hobby h1_0 where h1_0.hobby_name=?
		 * Hibernate: select h1_0.id,h1_0.hobby_name from hobby h1_0 where h1_0.hobby_name=?
		 * Hibernate: delete from post_hobby
		 */

		// THEN
		Hobby foundHobbyBaseball = hobbyRepository.findByHobbyName("야구").orElseThrow(NotFoundHobbyException::new);
		Hobby foundHobbySoccer = hobbyRepository.findByHobbyName("축구").orElseThrow(NotFoundHobbyException::new);
		boolean hasBaseballInPost = post.getPostHobbies().stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(foundHobbyBaseball));
		boolean hasSoccerLeftInPost = post.getPostHobbies().stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(foundHobbySoccer));
		assertTrue(hasBaseballInPost);
		assertTrue(hasSoccerLeftInPost);
		/**
		 * Hibernate: select p1_0.id,p1_0.contents,p1_0.created_at,p1_0.modified_at,p1_0.state_id,p1_0.title,p1_0.user_id from post p1_0
		 * Hibernate: select h1_0.id,h1_0.hobby_name from hobby h1_0
		 * Hibernate: select u1_0.id,u1_0.created_at,u1_0.email,u1_0.id_name,u1_0.introduction,u1_0.modified_at,u1_0.name,u1_0.nick_name,u1_0.password from user u1_0
		 * Hibernate: select c1_0.user_id,c1_0.id,c1_0.post_id,c1_0.text from comment c1_0 where c1_0.user_id=?
		 * Hibernate: select p1_0.user_id,p1_0.id,p1_0.contents,p1_0.created_at,p1_0.modified_at,p1_0.state_id,p1_0.title from post p1_0 where p1_0.user_id=?
		 */
	}

	@Test
	@DisplayName("로그인된 User를 입력받아 Post를 생성 및 업데이트 합니다.")
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
		boolean hasSoccerRelatesThisPost = hobbySoccer.getPostHobbies()
			.stream()
			.anyMatch(postHobby -> postHobby.getPost().equals(post));
		boolean hasBaseballRelatesThisPost = hobbyBaseball.getPostHobbies()
			.stream()
			.anyMatch(postHobby -> postHobby.getPost().equals(post));
		assertFalse(hasBaseBall);
		assertFalse(hasSoccer);
		assertFalse(hasSoccerRelatesThisPost);
		assertFalse(hasBaseballRelatesThisPost);
	}

	@Test
	@DisplayName("Post에 연관된 취미 엔티티들과의 연관관계를 모두 제거 시에, PostHobby Repository에서 관련된 PostHobby 엔티티를 탐색 불가능 해야합니다.")
	public void 연관관계모두제거_중간조인레포_탐색불가능() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);

		// WHEN
		post.removeHobbies();

		// THEN
		List<PostHobby> all = postHobbyRepository.findAll();
		assertEquals(0, all.size());
	}

	@Test
	@DisplayName("DELETE ALL POSTHOBBY JPQL 쿼리를 정상실행 합니다.")
	public void DELETE_ALL_POSTHOBBY테이블_JPQL쿼리실행() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball); // 연관관계 편의메서드
		post.addHobby(hobbySoccer);

		// WHEN
		postHobbyRepository.deleteAll();
	}
}