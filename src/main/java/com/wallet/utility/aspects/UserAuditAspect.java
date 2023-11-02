package com.wallet.utility.aspects;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAuditAspect {


    /**
     * Регистрирует анонимное действие в журнал аудита
    */
    @Around("@annotation(com.wallet.utility.aspects.AuditRaw)")
    public Object auditGetInfoFromRaw(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object obj = args[0];

        System.out.println(Arrays.toString(args));

        return joinPoint.proceed();
    }

    /**
     * Регистрирует действие пользователя в журнал аудита
     */
    @Around("@annotation(com.wallet.utility.aspects.AuditToken)")
    public Object auditGetInfoFromToken(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));

        return joinPoint.proceed();
    }
}
