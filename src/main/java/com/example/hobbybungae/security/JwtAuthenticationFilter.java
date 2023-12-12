package com.example.hobbybungae.security;

import com.example.hobbybungae.domain.user.dto.request.UserSignUpRequestDto;
import com.example.hobbybungae.domain.user.dto.response.UserResponseDto;
import com.example.hobbybungae.domain.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/hobby-bungae/v1/users/signin");
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			UserSignUpRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
				UserSignUpRequestDto.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDto.idName(),
					requestDto.password(),
					null
				)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException {
		User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
		UserResponseDto userResponseDto = new UserResponseDto(user.getIdName(), user.getName());

		String token = jwtUtil.createToken(user.getIdName());
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

		ObjectMapper objectMapper = new ObjectMapper();

		String result = objectMapper.writeValueAsString(userResponseDto);

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		String result = objectMapper.writeValueAsString("회원을 찾을 수 없습니다.");

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);
	}

}
