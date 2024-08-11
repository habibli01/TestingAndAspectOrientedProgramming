package com.example.registration.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RegistrationLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.registration.service..*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Executing method: {}", joinPoint.getSignature().toShortString());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                logger.info("Method argument: {}", arg);
            }
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.registration.service..*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Method executed: {}", joinPoint.getSignature().toShortString());
        logger.info("Method return value: {}", result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.registration.service..*(..))", throwing = "exception")
    public void logAfterMethodException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {}", joinPoint.getSignature().toShortString());
        logger.error("Exception message: {}", exception.getMessage(), exception);
    }

    @Around("execution(* com.example.registration.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("Method {} executed in {} ms", joinPoint.getSignature().toShortString(), timeTaken);
        }
    }
}
