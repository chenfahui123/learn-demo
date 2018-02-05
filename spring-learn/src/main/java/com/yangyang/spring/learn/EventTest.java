package com.yangyang.spring.learn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chenshunyang
 * @create 2017-12-27 11:24
 **/
public class EventTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-application.xml");
        EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
        context.publishEvent(event);
    }
}
