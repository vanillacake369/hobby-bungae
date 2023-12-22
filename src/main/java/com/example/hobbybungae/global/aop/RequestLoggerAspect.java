package com.example.hobbybungae.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "Request Logger")
public class RequestLoggerAspect {

	private static void logArg(Object arg) {
		log.info("parameter type = {}", arg.getClass().getSimpleName());
		log.info("parameter value = {}", arg);
	}

	private static void logJoinPointMethod(JoinPoint joinPoint) {
		String joinPointMethodName = joinPoint.getSignature().getName();
		log.info("========= Method Name : {} =========", joinPointMethodName);
	}

//	@Pointcut("execution(* com.example.hobbybungae.domain.user.controller.*(..))")
//	private void userRequestPointCut() {
//	}

	@Pointcut("execution(* com.example.hobbybungae.domain.comment.controller..*(..))")
	private void commentRequestPointCut() {
	}

	@Pointcut("execution(* com.example.hobbybungae.domain.hobby.controller..*(..))")
	private void hobbyRequestPointCut() {
	}

	@Pointcut("execution(* com.example.hobbybungae.domain.post.controller..*(..))")
	private void postRequestPointCut() {
	}

	@Pointcut("execution(* com.example.hobbybungae.domain.userProfile.controller..*(..))")
	private void userProfileRequestPointCut() {
	}

	@Around("commentRequestPointCut() || hobbyRequestPointCut() || postRequestPointCut() || userProfileRequestPointCut() ")
//	@Around("userRequestPointCut() || commentRequestPointCut() || hobbyRequestPointCut() || postRequestPointCut() || userProfileRequestPointCut() ")
	public Object executionTimeDAO(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();
		Object[] arguments = pjp.getArgs();
		log.info(className + " Method : " + methodName + " Arguments passed :" + new ObjectMapper().writeValueAsString(arguments));
		long startTime = System.currentTimeMillis();
		Object obj = pjp.proceed();
		long endTime = System.currentTimeMillis();
		log.info(className + " method : " + methodName + " Execution time: " + (endTime - startTime) + "ms");
		log.info(className + " Method: " + methodName + " Response received : " + new ObjectMapper().writeValueAsString(obj));
		return obj;
	}

	@Before("commentRequestPointCut() || hobbyRequestPointCut() || postRequestPointCut() || userProfileRequestPointCut() ")
//	@Before("userRequestPointCut() || commentRequestPointCut() || hobbyRequestPointCut() || postRequestPointCut() || userProfileRequestPointCut() ")
	public void beforeRequestLog(JoinPoint joinPoint) {
		logJoinPointMethod(joinPoint);

		Object[] joinPointArgs = joinPoint.getArgs();
		if (joinPointArgs.length == 0) {
			log.info("No Parameter");
			return;
		}
		for (Object arg : joinPointArgs) {
			logArg(arg);
		}
	}

	@AfterReturning(value = "commentRequestPointCut() || hobbyRequestPointCut() || postRequestPointCut() || userProfileRequestPointCut() "
		, returning = "returnObj")
//	@AfterReturning(value = "userRequestPointCut() || commentRequestPointCut() || hobbyRequestPointCut() || postRequestPointCut() || userProfileRequestPointCut() "
//		, returning = "returnObj")
	public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
		logJoinPointMethod(joinPoint);
		logArg(returnObj);
	}
}
