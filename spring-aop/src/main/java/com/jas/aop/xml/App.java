package com.jas.aop.xml;

import com.jas.aop.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jas
 * @create 2018-05-25 10:24
 **/
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-xml.xml");
        Person p = (Person) context.getBean("personImpl");
        
        p.sayHello();
        p.sayBye(" See You Again");
    }
}
