package com.example.hobbybungae.domain.hobby.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.hobbybungae.config.WebSecurityConfig;
import com.example.hobbybungae.domain.hobby.dto.HobbyRequestDto;
import com.example.hobbybungae.domain.hobby.service.HobbyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
	value = {HobbyController.class},
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
	}
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class HobbyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HobbyService hobbyService;


	@Test
	@DisplayName("취미 컨트롤러 생성 요청 처리를 합니다.")
	public void 취미_생성_요청처리() throws Exception {
		// GIVEN
		HobbyRequestDto requestDto = new HobbyRequestDto("취미");

		mockMvc
			// WHEN
			.perform(
				post("/hobby-bungae/v1/categories")
					.contentType(MediaType.APPLICATION_JSON)
					.content(
						new ObjectMapper().registerModule(new JavaTimeModule())
							.writeValueAsString(requestDto))
			)
			.andDo(print())
			// THEN
			.andExpect(status().isOk());
	}
}