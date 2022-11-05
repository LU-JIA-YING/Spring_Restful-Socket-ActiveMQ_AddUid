package com.example.Spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

//AOP=>https://www.javainuse.com/spring/spring-boot-aop
//定義一切面類
@Aspect
@Component
public class ServiceAop {
    //在不修改源代码的情况下，可以加入並執行自己的程式
    @Before(value = "execution(* com.example.Spring.service.TransferService.*(..))")
    public void servicePointcut(JoinPoint joinPoint) {
        // Method Information
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        System.out.println("執行" + methodName);
    }
}

