package com.wallet.utility.aspects;

//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.ProceedingJoinPoint;
//
//@Aspect
//public class SpeedAspect {
//    @Pointcut("within(@com.wallet.utility.aspects.SpeedTest *) && execution(* *(..))")
//    public void annotatedBySpeedTest() {
//    }
//
//    @Around("com.wallet.utility.aspects.SpeedAspect.annotatedBySpeedTest()")
//    public Object speedtesting(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        System.out.println("Вызван метод: " + proceedingJoinPoint.getSignature());
//        Long startTime = System.currentTimeMillis();
//        Object result = proceedingJoinPoint.proceed();
//        Long endTime = System.currentTimeMillis();
//        Long speedTime = endTime - startTime;
//        System.out.println("Метод " + proceedingJoinPoint.getSignature() + " был обработан за " + speedTime + " ms.");
//        return result;
//    }
//}
