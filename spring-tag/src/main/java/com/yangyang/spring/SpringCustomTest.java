package com.yangyang.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenshunyang
 * @create 2017-12-26 19:32
 **/
public class SpringCustomTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("test.xml");
        User user = (User) ac.getBean("testBean");
        System.out.println(user.getUserName()+","+user.getEmail());
    }
}
