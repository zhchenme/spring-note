package com.jas.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面
 * 也可以在切面上使用@Order注解用于指定切面的优先级，值越小优先级越高
 * 
 * @author Jas
 * @create 2018-05-25 9:09
 **/
@Aspect
@Component
public class LoggingAspect {

    /**
     * 声明一个切入点表达式，用于重用
     * 后面的通知直接使用方法名即可
     * 这里只是做一个示例，并没有使用
     */
    @Pointcut("execution(* com.jas.aop.bean.Person.*(..))")
    public void declareJoinPointExpression() {}
    
    /**
     * 前置通知，拦截目标对象中的sayHello()方法（public void类型）
     * 
     * "execution(* com.jas.aop.bean.Person.*(..))" 表示拦截Person类下任意修饰符与任意返回值且任意参数的方法
     * "execution(* com.jas.aop.bean.*.*(..))" 表示拦截该包下所有匹配的方法任意参数且任意返回值
     * @param joinPoint     用于获取拦截方法中的信息
     */
    @Before("execution(public void com.jas.aop.bean.PersonImpl.sayHello())")
    public void beforeMethod1(JoinPoint joinPoint) {
        System.out.println("在目标方法执行之前执行" + ", 要拦截的方法是：" + joinPoint.getSignature());
    }

    /**
     * 环绕通知，可用于完成所有通知完成的功能
     * 
     * @param joinPoint ProceedingJoinPoint 的实例，只能作用于环绕通知
     * @return
     * @throws Throwable
     */
    @Around("execution(public void com.jas.aop.bean.PersonImpl.sayBye(String))")
    public Object beforeMethod2(ProceedingJoinPoint joinPoint) throws Throwable {
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
