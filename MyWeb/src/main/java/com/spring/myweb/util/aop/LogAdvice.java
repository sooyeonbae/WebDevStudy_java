package com.spring.myweb.util.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect	//AOP를 적용시킬 클래스
@Component //빈등록
public class LogAdvice {
	
	//로그 찍을 변수
	private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

	/*
	 준비하신 로직(Advice)을 어떤 시점(JointPoint)에서 사용하게 할 지를 정해주실 수 있습니다. (=PointCut)
	 PointCut 종류 : @before, @after, @afterThrowing(예외)
	 @around는 위에 있는 세가지 pointCut을 한번에 처리할 수 있도록 해줍니다.
	 메서드 실행 권한을 가지고, 타겟메서드랑 접목시켜서 사용할수 있게 해줍니다.
	 규칙 : 반환타입은 Object,
	 		매개변수로 메서드의 실행시점을 결정짓는 ProceedingJoinPoint타입변수를 선언합니다.
	 (ProceedingJoinPoint는 AOP의 대상이되는 Target이나 파라미터 등을 파악할 뿐만 아니라, 직접 실행을 결정할수도있습니다.)
	 
	 execution(accessModi package class method argument)
	 			접근제한자							매개변수
	 */
	@Around("execution(* com.spring.myweb.*.service.*Service.*(..) )") 
	//모든접근제한자(보통 이렇게함), 저 서비스패키지 경로안모든매개변수(..)를받는 모든메서드(*)
	public Object aroundLog(ProceedingJoinPoint jp) {
		
		long start = System.currentTimeMillis();
		logger.info("실행 클래스 : " + jp.getTarget());
		logger.info("실행 메서드 : " + jp.getSignature().toString());
		logger.info("매개값 : " + Arrays.toString(jp.getArgs()));
		
		
		Object result = null;
		try {
			result = jp.proceed(); //타겟 메서드의 실행 요구
		} catch (Throwable e) {
			e.printStackTrace();
		}	
		
		long end = System.currentTimeMillis();
		
		logger.info("메서드 소요시간 : " + (end-start)*0.001 + "초");
		
		//위에 작성한 메서드의 실행내용은 proxy환경(가상환경)에서 돌아가는 중이기 때문에
		return result;	//proceed() 의 결과를 반환해야 정상흐름으로 돌아갑니다.
		
	}
	
}
