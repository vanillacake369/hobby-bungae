package com.example.hobbybungae.domain.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.hobby.service.HobbyService;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.dto.PostResponseDto;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.InvalidPostModifierException;
import com.example.hobbybungae.domain.post.repository.PostRepository;
import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.state.service.StateService;
import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.global_exception.ErrorCode;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({PostService.class, Post.class, Comment.class, User.class, HobbyService.class, StateService.class})
class PostServiceTest {

	@Autowired
	private PostService postService;

	@Autowired
	private StateService stateService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private HobbyRepository hobbyRepository;

	public static Stream<Arguments> getPostRequestAndUser() {
		return Stream.of(
			Arguments.of(
				new PostRequestDto("졸려요",
					"잠 좀 자게 해주삼",
					new State("경기도", "수원시", "장안구"),
					List.of(new Hobby("코딩"),
						new Hobby("자동차")
					)
				),
				User.builder().id(1L).name("지순이").idName("지순지순").password("지순비번123").introduction("지순이소개")
					.build()
			)
		);
	}

	private static void execute() {
		PostService.validateUserIsAuthor(1L, 2L);
	}

	@BeforeEach
	void setUp() {
		Hobby coding = new Hobby("코딩");
		Hobby car = new Hobby("자동차");
		Hobby chest = new Hobby("가슴");
		Hobby shoulder = new Hobby("어깨");
		hobbyRepository.save(coding);
		hobbyRepository.save(car);
		hobbyRepository.save(chest);
		hobbyRepository.save(shoulder);
//		PostRequestDto codePostRequest = new PostRequestDto("같이 코딩할 사람 모아요",
//			"코딩 좋아하는 사람과 모각코 스터디를 모집합니다.",
//			new State("경기도", "화성시", "장충동"),
//			List.of(coding, car)
//		);
//		PostRequestDto workoutPostRequest = new PostRequestDto("같이 운동할 사람 모아요",
//			"운동 좋아하는 사람과 벤치프레스 스터디를 모집합니다.",
//			new State("경기도", "화성시", "장충동"),
//			List.of(chest, shoulder)
//		);
//		User user = User.builder().id(1L).name("유저1").idName("유저1아이디").password("유저1비밀번호").introduction("유저1소개")
//			.build();
//		Post codePost = Post.of(codePostRequest, user);
//		Post workoutPost = Post.of(workoutPostRequest, user);
//		postRepository.save(codePost);
//		postRepository.save(workoutPost);
	}

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("회원이 게시글의 주인이라면 정상처리합니다.")
	public void 게시글주인_해피케이스() {
		// WHEN
		PostService.validateUserIsAuthor(1L, 1L);
	}

	@Test
	@DisplayName("회원이 게시글의 주인이 아니라면 예외를 던집니다.")
	public void 게시글주인_언해피케이스() {
		// WHEN
		// THEN
		InvalidPostModifierException invalidPostModifierException = assertThrows(InvalidPostModifierException.class,
			PostServiceTest::execute);

		assertEquals(invalidPostModifierException.getErrorCode(), ErrorCode.INVALID_POST_MODIFIER);
	}

	@ParameterizedTest
	@DisplayName("게시글을 작성합니다.")
	@MethodSource("getPostRequestAndUser")
	@Transactional
	public void 게시글_작성(PostRequestDto requestDto, User user) {
		// WHEN
		PostResponseDto postResponseDto = postService.addPost(requestDto, user);

		// THEN
		assertEquals(user, postResponseDto.user());
		assertEquals("졸려요", postResponseDto.title());
	}
}