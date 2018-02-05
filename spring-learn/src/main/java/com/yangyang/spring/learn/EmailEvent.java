package com.yangyang.spring.learn;


import org.springframework.context.ApplicationEvent;

/**
 * @author chenshunyang
 * @create 2017-12-27 11:14
 **/
public class EmailEvent extends ApplicationEvent {

    public String address;
    public String text;

    public EmailEvent(Object source){
        super(source);
    }

    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

    public void print(){
        System.out.println("hello spring event!");
    }

}
