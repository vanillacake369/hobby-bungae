package com.example.hobbybungae.domain.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.service.PostService;
import com.example.hobbybungae.domain.state.entity.State;
import com.example.hobbybungae.domain.user.entity.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(PostController.class)
@ActiveProfiles("test")
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();
	}

	@DisplayName("게시글을 생성합니다.")
	@Test
	void addPost() throws Exception {
		// GIVEN
		PostRequestDto postRequestDto = new PostRequestDto("졸려요",
			"잠 좀 자게 해주삼",
			new State("경기도", "수원시", "장안구"),
			List.of(new Hobby("코딩"),
				new Hobby("자동차")
			)
		);
		User user = User.builder().id(1L).name("지순이").idName("지순지순").password("지순비번123").introduction("지순이소개")
			.build();

		// WHEN
		mockMvc.perform(post("/hobby-bungae/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("""
					{
					    "title" : "게시글 제목",
					    "content" : "게시글 내용",
					    "state" : {
					        "stateDo" : "충청북도",
					        "stateSi" : "청주시",
					        "stateGu" : "상당구"
					    },
					    "hobbies" : "축구"
					}"""))
			.andExpect(status().isOk());

		// THEN
	}

}