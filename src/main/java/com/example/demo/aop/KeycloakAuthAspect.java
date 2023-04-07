package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public interface KeycloakAuthAspect {
    Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable;
}
