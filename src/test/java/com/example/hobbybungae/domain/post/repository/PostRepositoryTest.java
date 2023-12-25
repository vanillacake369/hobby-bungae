package com.example.hobbybungae.domain.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.hobbybungae.config.QueryDslConfiguration;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.post.unit.PostSelectCondition;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.util.ProxyUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(QueryDslConfiguration.class)
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostHobbyRepository postHobbyRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	@Autowired
	UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

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
		Post post = Post.builder()
			.id(1L)
			.title("postTitle")
			.contents("postContents")
			.user(user)
			.build();
		this.user = userRepository.save(user);
		this.post = postRepository.save(post);
	}

	@AfterEach
	void tearDown() {
		postHobbyRepository.deleteAll();
		postRepository.deleteAll();
		hobbyRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("postHobbyRepo에 대하여 어떤 구현체를 autowired 하는지 확인합니다.")
	public void postHobbyRepoImpl_확인() {
		// WHEN
		Class<?> postHobbyRepoImpl = AopProxyUtils.ultimateTargetClass(postHobbyRepository);
		Class<?> postHobbyRepoInterface = AopProxyUtils.proxiedUserInterfaces(postHobbyRepository)[0];

		// THEN
		System.out.println("postHobbyRepoImpl = " + postHobbyRepoImpl);
		System.out.println("postHobbyRepoImplClassInfo = " + postHobbyRepoInterface);
	}

	@Test
	@DisplayName("PostHobby 중간조인테이블에서 데이터 삭제 이후, left over entity가 존재합니다.")
	public void 중간조인테이블_데이터삭제_leftover_entity존재() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);

		// WHEN
		postHobbyRepository.deleteAllByJpql();

		// THEN
		Hobby foundHobbyBaseball = hobbyRepository.findByHobbyName("야구").orElseThrow(NotFoundHobbyException::new);
		Hobby foundHobbySoccer = hobbyRepository.findByHobbyName("축구").orElseThrow(NotFoundHobbyException::new);
		boolean hasBaseballInPost = post.getPostHobbies().stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(foundHobbyBaseball));
		boolean hasSoccerLeftInPost = post.getPostHobbies().stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(foundHobbySoccer));
		assertTrue(hasBaseballInPost);
		assertTrue(hasSoccerLeftInPost);
	}

	@Test
	@DisplayName("PostHobby 중간조인테이블에서 영속성 sync 데이터 삭제 이후, left over entity가 존재하지 않습니다.")
	public void 중간조인테이블_데이터삭제_영속성초기화() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball);
		post.addHobby(hobbySoccer);

		// WHEN
		postHobbyRepository.deleteAllByJpqlSyncPersistence();

		// THEN
		Hobby foundHobbyBaseball = hobbyRepository.findByHobbyName("야구").orElseThrow(NotFoundHobbyException::new);
		Hobby foundHobbySoccer = hobbyRepository.findByHobbyName("축구").orElseThrow(NotFoundHobbyException::new);
		boolean hasBaseballInPost = post.getPostHobbies().stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(foundHobbyBaseball));
		boolean hasSoccerLeftInPost = post.getPostHobbies().stream()
			.anyMatch(postHobby -> postHobby.getHobby().equals(foundHobbySoccer));
		assertFalse(hasBaseballInPost);
		assertFalse(hasSoccerLeftInPost);
	}

	@Test
	@DisplayName("DELETE ALL POSTHOBBY CRUDRepo의 쿼리는 실행되지 않습니다.")
	public void DELETE_ALL_POSTHOBBY테이블_CRUDRepo쿼리실행() {
		// GIVEN
		Hobby hobbyBaseball = Hobby.builder().hobbyName("야구").build();
		Hobby hobbySoccer = Hobby.builder().hobbyName("축구").build();
		hobbyRepository.save(hobbyBaseball);
		hobbyRepository.save(hobbySoccer);
		post.addHobby(hobbyBaseball); // 연관관계 편의메서드
		post.addHobby(hobbySoccer);

		// WHEN
		postHobbyRepository.deleteAll(); // SimpleJpaRepository

		// THEN
		System.out.println("postHobbyRepository.findAll().size() = " + postHobbyRepository.findAll().size());
		assertNotEquals(0, postHobbyRepository.findAll().size());
	}

	@Test
	@DisplayName("SimpleJpaRepository의 DELETE문을  직접 실행해봅니다.")
	public void SimpleJpaRepositoryDELETE문_직접실행() {
		// GIVEN
		// entityInformation
		JpaEntityInformation<Post, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(Post.class, entityManager);

		// WHEN
		Assert.notNull(post, "Entity must not be null");

		if (entityInformation.isNew(post)) {
			return;
		}

		Class<?> type = ProxyUtils.getUserClass(post);

		Post existing = (Post) entityManager.find(type, entityInformation.getId(post));
		// if the entity to be deleted doesn't exist, delete is a NOOP
		if (existing == null) {
			return;
		}

		entityManager.remove(entityManager.contains(post) ? post : entityManager.merge(post)); // 쓰기 지연 저장소에 저장

		// THEN
		assertEquals(Post.class, type);
		assertFalse(entityManager.contains(post));
		assertNotNull(existing);
	}

	@Test
	@DisplayName("SimpleJpaRepository의 DELETE문을  직접 실행한 이후, flush를 직접 하여 트랜잭션 종료 이전에 수행하도록 변경하여 수행해봅니다.")
	public void SimpleJpaRepositoryDELETE문_직접실행_이후_flush() {
		// GIVEN
		// entityInformation
		JpaEntityInformation<Post, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(Post.class, entityManager);

		// WHEN
		Assert.notNull(post, "Entity must not be null");

		if (entityInformation.isNew(post)) {
			return;
		}

		Class<?> type = ProxyUtils.getUserClass(post);

		Post existing = (Post) entityManager.find(type, entityInformation.getId(post));
		// if the entity to be deleted doesn't exist, delete is a NOOP
		if (existing == null) {
			return;
		}

		entityManager.remove(entityManager.contains(post) ? post : entityManager.merge(post));
		entityManager.flush();

		// THEN
		assertEquals(Post.class, type);
		assertFalse(entityManager.contains(post));
		assertNotNull(existing);
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
		postHobbyRepository.deleteAllByJpql(); // JPQL

		// THEN
		System.out.println("postHobbyRepository.findAll().size() = " + postHobbyRepository.findAll().size());
		assertEquals(0, postHobbyRepository.findAll().size());
	}

	@Test
	@DisplayName("로그인된 User를 입력받아 Post를 생성 및 업데이트 합니다.")
	public void 로그인된_User의_Post_생성() {
		// GIVEN
		User user = User.builder()
			.name("user")
			.idName("userIdName")
			.email("userEmail@email.com")
			.nickName("userNickName")
			.password("userPassword!@#")
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
	@DisplayName("Condition에 따른 Post 리스트를 조회합니다.")
	public void querydsl_Post조회_ByCondition() {
		// GIVEN
		PostSelectCondition condition = PostSelectCondition.builder()
			.userId(user.getId())
			.title(post.getTitle())
			.contents(post.getContents())
			.build();

		// WHEN
		List<Post> posts = postRepository.findPostsBy(condition);

		// THEN
		assertEquals(1, posts.size());
		assertEquals(post, posts.get(0));
	}
}