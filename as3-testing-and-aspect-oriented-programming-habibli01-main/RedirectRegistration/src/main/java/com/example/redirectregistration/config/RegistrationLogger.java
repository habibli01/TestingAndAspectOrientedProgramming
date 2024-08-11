package com.example.redirectregistration.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RegistrationLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.redirectregistration.controller..*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Executing method: {}", joinPoint.getSignature().toShortString());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                logger.info("Method argument: {}", arg);
            }
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.redirectregistration.controller..*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Method executed: {}", joinPoint.getSignature().toShortString());
        logger.info("Method return value: {}", result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.redirectregistration.controller..*(..))", throwing = "exception")
    public void logAfterMethodException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {}", joinPoint.getSignature().toShortString());
        logger.error("Exception message: {}", exception.getMessage(), exception);
    }

    @Around("execution(* com.example.redirectregistration.controller..*(..))")
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
