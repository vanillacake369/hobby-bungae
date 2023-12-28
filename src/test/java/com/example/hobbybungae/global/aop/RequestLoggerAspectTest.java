package com.example.hobbybungae.global.aop;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.hobbybungae.domain.hobby.controller.HobbyController;
import com.example.hobbybungae.domain.hobby.dto.HobbyRequestDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RequestLoggerAspectTest {

	AspectJProxyFactory factory;
	HobbyController mockTarget;
	RequestLoggerAspect aspect;

	LocalDateTime requestTime;
	LocalDateTime responseTime;

	@BeforeEach
	void setUp() {
		mockTarget = mock(HobbyController.class);
		aspect = new RequestLoggerAspect();

		factory = new AspectJProxyFactory(mockTarget);
		factory.addAspect(aspect);
		factory.setProxyTargetClass(true);

		requestTime = LocalDateTime.now();
		responseTime = requestTime.plusSeconds(1);
	}

	@Test
	@DisplayName("취미 컨트롤러 요청 처리에 대한 method와 parameter를 logging 합니다.")
	public void 취미요청처리_메소드파라미터_로깅() throws Throwable {
		// GIVEN
		MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
		HobbyRequestDto requestDto = new HobbyRequestDto("취미");

		// WHEN
		when(LocalDateTime.now())
			.thenReturn(requestTime, responseTime)
			.thenCallRealMethod();
		HobbyController proxy = factory.getProxy();
		proxy.postHobby(requestDto);

		// THEN
		// https://junhyunny.github.io/spring-boot/test-driven-development/improve-feign-client-aop-test/
		// 흠,,, 그 다음에 어떻게 테케를 짜주어야 할까,,,
	}
}