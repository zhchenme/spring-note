package com.jas.aop.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class LoggingAspect {
    
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("在目标方法执行之前执行" + ", 要拦截的方法是：" + joinPoint.getSignature());
    }
    
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        // 判断目标方法参数，满足条件修改参数值
        if(" See You Again".equals(args[0])) {
            args[0] = " See You Again ...";
        }
        
        // 在目标方法执行之前执行，相当于前置通知
        System.out.println("这是一个前置通知");
        // 执行目标方法
        Object result = joinPoint.proceed(args);
        // 在目标方法执行之后之后，相当于后置通知
        System.out.println("这是一个后置通知");
        
        return result;
    }
}
