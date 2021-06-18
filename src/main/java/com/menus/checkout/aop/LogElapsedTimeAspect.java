package com.menus.checkout.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogElapsedTimeAspect {

    @Around("@annotation(LogElapsedTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long executionTime = System.currentTimeMillis() - start;

            log.info("Elapsed time to execute {} is {} ms", joinPoint.getSignature().getName(),
                executionTime);
        }

    }

}
