package com.yangyang.corejava.java8;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenshunyang on 2017/1/11.
 */
public class LambdasTest01 {

    interface  Message{
        void sendMessage(Runnable runnable);
    }

    static class PhoneMessage implements Message{
        private AtomicInteger num = new AtomicInteger(0);
        @Override
        public void sendMessage(Runnable runnable) {
            runnable.run();
            System.out.println("--------------我是分界线" + (num.getAndIncrement()) + "-----------------");
        }
    }

    public static void main(String[] args) {
        PhoneMessage phoneMessage = new PhoneMessage();

        //jdk8以前写法
        phoneMessage.sendMessage(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名函数写法");
            }
        });

        //lambdas表达方式
        phoneMessage.sendMessage(()->System.out.println("lambdas表达方式"));
    }
}
