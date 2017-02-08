package com.yangyang.corejava.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MyThread extends Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName() + "正在执行。。。");
    }
}

class LongThread extends Thread{
    public void run(){
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class SingleThreadExecutor {
       public static void main(String[] args) {
        ExecutorService pool=Executors.newSingleThreadExecutor();
        
        Thread t1=new MyThread();
        Thread t2=new LongThread();
        Thread t3=new MyThread();
        Thread t4=new MyThread();
        
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        //关闭线程池
        pool.shutdown();
    }
}
