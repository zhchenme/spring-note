package com.jas.aop.bean;

import org.springframework.stereotype.Component;

/**
 * @author Jas
 * @create 2018-05-25 9:01
 **/
@Component
public class PersonImpl implements  Person{
    @Override
    public void sayHello() {
        System.out.println("Hello AOP");
    }

    @Override
    public void sayBye(String info) {
        System.out.println("Bye AOP" + info);
    }
}
