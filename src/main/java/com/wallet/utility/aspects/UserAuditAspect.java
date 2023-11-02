package com.wallet.utility.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class UserAuditAspect {
    @Around("@within(com.wallet.utility.aspects.Audit) || @annotation(com.wallet.utility.aspects.Audit)")
    public Object auditGetInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Object[] args = joinPoint.getArgs();

        System.out.println(Arrays.toString(args));
//        // Ваша логика аудита
//        String action = "GetInfo"; // Название действия
//        String details = "User: " + username + ", Method: " + method.getName() + ", Parameters: " + Arrays.toString(args);
//
//        // Вызов сервиса аудита для сохранения информации
//        auditService.auditUserAction(username, action, details);

        // Вызов метода, к которому применяется аспект
        return joinPoint.proceed();
    }
}
