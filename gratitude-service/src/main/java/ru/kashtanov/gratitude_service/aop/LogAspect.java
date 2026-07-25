package ru.kashtanov.gratitude_service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Viktor Кashtanov
 */
@Aspect
@Component
public class LogAspect {


    @Before("execution(* ru.kashtanov.gratitude_service.service.GratitudeService.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("АСПЕКТ _ Метод " + joinPoint.getSignature().getName() + " начал работу");
    }

    @AfterReturning("execution(* ru.kashtanov.gratitude_service.service.GratitudeService.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        System.out.println("АСПЕКТ _ Метод " + joinPoint.getSignature().getName() + " завершил работу");
    }
}
