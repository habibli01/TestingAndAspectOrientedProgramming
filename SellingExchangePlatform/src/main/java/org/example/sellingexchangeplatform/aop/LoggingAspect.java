package org.example.sellingexchangeplatform.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* org.example.sellingexchangeplatform.service.impl.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Method Called: " + joinPoint.getSignature().getName() + " with arguments: " + joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* org.example.sellingexchangeplatform.service.impl.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method Executed: " + joinPoint.getSignature().getName() + " with result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* org.example.sellingexchangeplatform.service.impl.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Method Threw Exception: " + joinPoint.getSignature().getName(), error);
    }
}
