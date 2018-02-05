package com.yangyang.spring.learn;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * /监听ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用
 * @author chenshunyang
 * @create 2017-12-27 13:19
 **/
public class SpringInitListener implements ApplicationListener<ContextRefreshedEvent>{
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("spring容易初始化完毕================================================");
    }
}
