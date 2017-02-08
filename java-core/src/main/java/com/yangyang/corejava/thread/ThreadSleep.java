package com.yangyang.corejava.thread;

class MyRunnable implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName()+"线程第" + i + "次执行！");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
    
}

public class ThreadSleep {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable(),"t1");
        Thread t2 = new Thread(new MyRunnable(),"t2");
        t1.start();
        t2.start();
    }
}
