package com.wallet.utility.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@within(com.wallet.utility.aspects.SpeedTest) || @annotation(com.wallet.utility.aspects.SpeedTest)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Method " + joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return result;
    }
}
