/**
 * 
 */
package ai.patient.util;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspects that time methods execution. 
 * 
 * @author evgeniy.sharapov
 *
 */
@Aspect
@Component
@Slf4j
public class MethodTimingAspect {
	
	@Around("ai.patient.util.AspectPointcuts.apiLayerMethods()")
	public void timeDataLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		Instant start = Instant.now();

		joinPoint.proceed();

		Instant end = Instant.now(); 
		
		log.debug("Time Taken by {} is {}", joinPoint, Duration.between(start, end));
	}
}