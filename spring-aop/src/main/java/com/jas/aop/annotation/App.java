package com.jas.aop.annotation;

import com.jas.aop.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jas
 * @create 2018-05-25 9:02
 **/
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-annotation.xml");
        Person p = (Person) context.getBean("personImpl");
        
        p.sayHello();
        p.sayBye(" See You Again");

        // 使用的是jdk动态代理
        System.out.println(p.getClass());
    }
}
