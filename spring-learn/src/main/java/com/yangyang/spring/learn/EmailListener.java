package com.yangyang.spring.learn;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author chenshunyang
 * @create 2017-12-27 11:19
 **/
public class EmailListener implements ApplicationListener{
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof EmailEvent){
            EmailEvent emailEvent = (EmailEvent)event;
            emailEvent.print();
            System.out.println("the source is:"+emailEvent.getSource());
            System.out.println("the address is:"+emailEvent.address);
            System.out.println("the email's context is:"+emailEvent.text);
        }
    }
}
